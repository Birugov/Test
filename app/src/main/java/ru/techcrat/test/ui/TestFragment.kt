package ru.techcrat.test.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import ru.techcrat.test.BaseApplication
import ru.techcrat.test.R
import ru.techcrat.test.data.Area
import ru.techcrat.test.data.FullArea
import ru.techcrat.test.data.LocalPoint
import ru.techcrat.test.databinding.FragmentTestBinding
import ru.techcrat.test.di.components.DaggerAppComponent
import ru.techcrat.test.viewmodels.TestFragmentViewModel
import ru.techcrat.test.viewmodels.ViewModelFactory
import javax.inject.Inject


class TestFragment : Fragment(), InputListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: TestFragmentViewModel by viewModels {
        factory
    }
    val points: ArrayList<Point> = ArrayList()
    private val binding: FragmentTestBinding by viewBinding()
    var savedPoints: ArrayList<LocalPoint> = ArrayList()
    var savedAreas: ArrayList<Area> = ArrayList()
    private val fullAreas: ArrayList<FullArea> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey("b0360981-619d-485c-be76-8f6a6e5b0385")
        MapKitFactory.initialize(context)
        DaggerAppComponent.builder().application(context?.applicationContext as BaseApplication)
            .build().inject(this)

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
        binding.mapView.map.addInputListener(this)
        makePoly()
        viewModel.initData()
        getValidList()

    }


    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onMapTap(p0: Map, p1: Point) {
        binding.mapView.map.mapObjects.addPlacemark(p1)
        points.add(p1)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        TODO("Not yet implemented")
    }

    private fun makePoly() {
        binding.drawBtn.setOnClickListener {
            if (points.size>=3) {
                val dialogFragment = NameDialogFragment()
                dialogFragment.show(childFragmentManager, "MyCustomDialog")
            }
            else
            makeText(context, "You should pick atleast 3 points to create field", LENGTH_LONG).show()
        }
    }

    fun drawPoly(sortedPoints:ArrayList<Point>) {
        val polygon = Polygon(LinearRing(sortedPoints), ArrayList<LinearRing>())
        binding.mapView.map.mapObjects.addPolygon(polygon)

    }

    private fun getValidList() {
        viewModel.initData()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            savedAreas.addAll(it)
            addCompleteArea()
            drawSavedPolys()
        }

        viewModel.getAllPoints.observe(viewLifecycleOwner) {
            savedPoints.addAll(it)
            addCompleteArea()
            drawSavedPolys()
        }

    }

    private fun addCompleteArea() {
        if (!savedAreas.isNullOrEmpty() && !savedPoints.isNullOrEmpty()) {
            for (elem in savedAreas) {
                val ymPoints: ArrayList<Point> = ArrayList()
                for (points in savedPoints) {
                    if (elem.areaName == points.areaName) {
                        val point = Point(points.latitude, points.longitude)
                        ymPoints.add(point)

                    }
                    val area = FullArea(elem.areaName, ymPoints)
                    fullAreas.add(area)
                }
            }
        }
    }

    private fun drawSavedPolys() {
        for (area in fullAreas) {
            val currentPoints = area.points
            for (point in currentPoints) {
                val currentPoint = Point(point.latitude, point.longitude)
                binding.mapView.map.mapObjects.addPlacemark(currentPoint)
            }
            val polygon = Polygon(LinearRing(area.points), ArrayList<LinearRing>())

            binding.mapView.map.mapObjects.addPolygon(polygon)

        }


    }

    fun reloadMap(){
        binding.mapView.map.mapObjects.clear()
        getValidList()

    }

}
