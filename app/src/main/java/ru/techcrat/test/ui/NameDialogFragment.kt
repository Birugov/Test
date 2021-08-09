package ru.techcrat.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yandex.mapkit.geometry.Point
import ru.techcrat.test.BaseApplication
import ru.techcrat.test.R
import ru.techcrat.test.data.Area
import ru.techcrat.test.databinding.FragmentNameDialogBinding
import ru.techcrat.test.di.components.DaggerAppComponent
import ru.techcrat.test.extentions.GrahamPoint
import ru.techcrat.test.extentions.convexHull
import ru.techcrat.test.viewmodels.DialogViewModel
import ru.techcrat.test.viewmodels.ViewModelFactory
import javax.inject.Inject


class NameDialogFragment : DialogFragment() {

    @Inject
    lateinit var factory:ViewModelFactory

    private val binding: FragmentNameDialogBinding by viewBinding()
    private val viewModel:DialogViewModel by viewModels{
        factory
    }
    private val sortedYMPoints:ArrayList<Point> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerAppComponent.builder().application(context?.applicationContext as BaseApplication)
            .build().inject(this)
        return inflater.inflate(R.layout.fragment_name_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionOk.setOnClickListener {
            val name = binding.input.text.toString()
            val fragment = activity?.supportFragmentManager?.findFragmentByTag("fragment") as TestFragment
            val area = Area(0, name)
            area.let { it1 -> viewModel.addArea(it1) }
            sortPoints(fragment.points)
            viewModel.addPoints(sortedYMPoints, name)
            fragment.drawPoly(sortedYMPoints)
            fragment.points.clear()
            dialog?.dismiss()

        }
        binding.actionCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    fun sortPoints(points: ArrayList<Point>){
        val grahamPoints: ArrayList<GrahamPoint> = ArrayList()
        for (elem in points){
            val grPoint = GrahamPoint(elem.latitude, elem.longitude)
            grahamPoints.add(grPoint)
        }
        val sortedPoints = convexHull(grahamPoints.toTypedArray())

        for (point in sortedPoints){
            val ymPoint = Point(point.latitude, point.longitude)
            sortedYMPoints.add(ymPoint)
        }

    }


}