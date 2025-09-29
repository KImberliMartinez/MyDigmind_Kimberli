package martinez.kimberli.digimind.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.BaseAdapter
import android.widget.GridView
import martinez.kimberli.digimind.R
import martinez.kimberli.digimind.ui.Task
import android.content.Context


class HomeFragment : Fragment() {

private var adaptador: RecordatorioAdapter? = null
    private lateinit var homeViewModel: HomeViewModel

    companion object {
        var tasks = ArrayList<Task>() // Lista global de recordatorios
        var first = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        if (first) {
            fillTasks()
            first = false
        }

        val gridview: GridView = root.findViewById(R.id.gridView)

        // Crear adaptador una sola vez
        adaptador = RecordatorioAdapter(requireContext(), tasks)
        gridview.adapter = adaptador

        return root
    }

    fun fillTasks() {
        tasks.add(Task("Practice 1", arrayListOf("Tuesday"), "17:30"))
        tasks.add(Task("Practice 2", arrayListOf("Monday", "Sunday"), "17:40"))
        tasks.add(Task("Practice 3", arrayListOf("Wednesday"), "14:00"))
        tasks.add(Task("Practice 4", arrayListOf("Saturday"), "11:00"))
        tasks.add(Task("Practice 5", arrayListOf("Friday"), "13:00"))
        tasks.add(Task("Practice 6", arrayListOf("Thursday"), "10:40"))
        tasks.add(Task("Practice 7", arrayListOf("Monday"), "12:00"))
    }

    fun actualizarLista() {
        adaptador?.notifyDataSetChanged()
    }
}



class RecordatorioAdapter : BaseAdapter {
    var tasks = ArrayList<Task>()
    var context: Context? = null

    constructor(context: Context, tasks: ArrayList<Task>) {
        this.context = context
        this.tasks = tasks
    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val task = tasks[position]
        val inflator = LayoutInflater.from(context)
        val vista = inflator.inflate(R.layout.recordatorio, null)

        val tvNombreRecordatorio = vista.findViewById<TextView>(R.id.tvNombreRecordatorio)
        val tvDiasRecordatorio = vista.findViewById<TextView>(R.id.tvDiasRecordatorio)
        val tvTiempoRecordatorio = vista.findViewById<TextView>(R.id.tvTiempoRecordatorio)

        tvNombreRecordatorio.text = task.title
        tvDiasRecordatorio.text = task.days.joinToString(", ")
        tvTiempoRecordatorio.text = task.time

        return vista
    }
}