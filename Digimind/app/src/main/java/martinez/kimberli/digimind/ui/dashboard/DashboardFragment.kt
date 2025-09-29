package martinez.kimberli.digimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import martinez.kimberli.digimind.R
import martinez.kimberli.digimind.ui.home.HomeFragment
import martinez.kimberli.digimind.ui.Task
import java.text.SimpleDateFormat
import java.util.Calendar

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, { })


        val btnHora: Button = root.findViewById(R.id.btnTime)
        val tvTiempo: TextView = root.findViewById(R.id.txtTiempo)

        btnHora.setOnClickListener {

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                btnHora.text = SimpleDateFormat("HH:mm").format(cal.time)

            }
            TimePickerDialog(
                root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true
            ).show()
        }

        val btnSave: Button = root.findViewById(R.id.btnRegister)
        val tvMensaje: TextView = root.findViewById(R.id.mensaje)
        val chbMonday: CheckBox = root.findViewById(R.id.chbMonday)
        val chbTuesday: CheckBox = root.findViewById(R.id.chbTuesday)
        val chbWednesday: CheckBox = root.findViewById(R.id.chbWednesday)
        val chbThursday: CheckBox = root.findViewById(R.id.chbThursday)
        val chbFriday: CheckBox = root.findViewById(R.id.chbFriday)
        val chbSaturday: CheckBox = root.findViewById(R.id.chbSaturday)
        val chbSunday: CheckBox = root.findViewById(R.id.chbSunday)

        btnSave.setOnClickListener {

            val title = tvMensaje.text.toString()
            val time = btnHora.text.toString()
            val days = ArrayList<String>()

            if (chbMonday.isChecked) days.add("Monday")
            if (chbTuesday.isChecked) days.add("Tuesday")
            if (chbWednesday.isChecked) days.add("Wednesday")
            if (chbThursday.isChecked) days.add("Thursday")
            if (chbFriday.isChecked) days.add("Friday")
            if (chbSaturday.isChecked) days.add("Saturday")
            if (chbSunday.isChecked) days.add("Sunday")

            // Validación: todos los campos deben estar llenos
            if (title.isNotEmpty() && time != "Select Time" && days.isNotEmpty()) {
                val task = Task(title, days, time)
                HomeFragment.tasks.add(task)

                // Actualizar la vista en HomeFragment
                val fragment =
                    parentFragmentManager.fragments.find { it is HomeFragment } as? HomeFragment
                fragment?.actualizarLista()

                tvMensaje.text = ""
                tvTiempo.text = ""
                chbMonday.isChecked = false
                chbTuesday.isChecked = false
                chbWednesday.isChecked = false
                chbThursday.isChecked = false
                chbFriday.isChecked = false
                chbSaturday.isChecked = false
                chbSunday.isChecked = false

                Toast.makeText(context, "New task added", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    context,
                    "One of the fields is missing: write the reminder, select day(s) and set time",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return root
    }
}