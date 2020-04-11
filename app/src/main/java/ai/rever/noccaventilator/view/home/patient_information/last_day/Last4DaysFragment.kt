package ai.rever.noccaventilator.view.home.patient_information.last_day

import ai.rever.noccaventilator.R
import ai.rever.noccaventilator.model.PatientData
import ai.rever.noccaventilator.room.getLastData4DaysFlowable
import ai.rever.noccaventilator.view.common.BaseFragment
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.android.synthetic.main.fragment_last_days.*
import kotlinx.android.synthetic.main.layout_item.*
import kotlinx.android.synthetic.main.layout_item.view.*
import kotlinx.android.synthetic.main.layout_item.view.tvName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Last4DaysFragment : BaseFragment() {
    override val title: String
        get() = "Patient Information"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_last_days, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutHeader.let {
            it.tvDate.setTypeface(it.tvDate.typeface, Typeface.BOLD)
            it.tvName.setTypeface(it.tvName.typeface, Typeface.BOLD)
            it.tvIdentifier.setTypeface(it.tvIdentifier.typeface, Typeface.BOLD)
            it.tvOperation.setTypeface(it.tvOperation.typeface, Typeface.BOLD)
            it.tvData.setTypeface(it.tvData.typeface, Typeface.BOLD)
        }

        rvLastDays.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        rvLastDays.adapter = adapter
    }

    val adapter by lazy {
        object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            val items: ArrayList<PatientData> = arrayListOf()

            init {
                getLastData4DaysFlowable
                    ?.subscribe {
                        items.clear()
                        items.addAll(it)
                        notifyDataSetChanged()
                    }?.addTo(compositeDisposable)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                    : RecyclerView.ViewHolder {
                return object: RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_item, parent, false)) {}
            }

            override fun getItemCount(): Int {
                return items.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val patientData = items[position]
                holder.itemView.let {
                    SimpleDateFormat("dd/MM/yyyy hh:mm:ss ", Locale.ENGLISH).apply {
                        it.tvDate.text = format(Date(patientData.millisValue))
                    }
                    it.tvName.text = patientData.name
                    it.tvIdentifier.text = patientData.id
                    it.tvOperation.text = if (patientData.dataType == "changedPatient") "Patient detail updated" else "P-T graph data"
                    it.tvData.text = if (patientData.pressureValue == null) "NaN" else "Pressure: ${patientData.pressureValue}"
                }
            }
        }
    }

}