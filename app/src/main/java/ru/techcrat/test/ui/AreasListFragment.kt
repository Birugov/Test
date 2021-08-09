package ru.techcrat.test.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yandex.mapkit.geometry.Point
import ru.techcrat.test.BaseApplication
import ru.techcrat.test.R
import ru.techcrat.test.adapters.AreasAdapter
import ru.techcrat.test.data.Area
import ru.techcrat.test.databinding.AreasListFragmentBinding
import ru.techcrat.test.databinding.AreasRecyclerElemBinding
import ru.techcrat.test.di.components.DaggerAppComponent
import ru.techcrat.test.viewmodels.AreasFragmentViewModel
import ru.techcrat.test.viewmodels.ViewModelFactory
import javax.inject.Inject

class AreasListFragment: BottomSheetDialogFragment() , AreasAdapter.OnDeleteClickListener{
    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var areasAdapter:AreasAdapter
    private val binding:AreasListFragmentBinding by viewBinding()
    private val viewModel:AreasFragmentViewModel by viewModels {
        factory
    }
    private val areas:ArrayList<Area> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerAppComponent.builder().application(context?.applicationContext as BaseApplication)
            .build().inject(this)
        return inflater.inflate(R.layout.areas_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        areasAdapter = AreasAdapter(this)
        initRecycler()
        addDataset()
    }
    private fun addDataset(){
        viewModel.initData()
        viewModel.readAllData.observe(viewLifecycleOwner){
            areas.addAll(it)
            areasAdapter.submitList(areas)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initRecycler() {
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        binding.areasRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = areasAdapter
            postponeEnterTransition()
            addItemDecoration(dividerItemDecoration)

        }
    }

    override fun onDeleteClick(position: Int) {
        val name = binding.areasRv.rootView.findViewById<TextView>(R.id.tv_area_name).text.toString()
        viewModel.deleteData(name)
        areas.removeAt(position)
        areasAdapter.notifyItemRemoved(position)



    }

}