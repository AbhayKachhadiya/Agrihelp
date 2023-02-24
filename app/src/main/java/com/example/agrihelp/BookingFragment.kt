package com.example.agrihelp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.agrihelp.models.Districts
import com.example.agrihelp.models.Machines
import com.example.agrihelp.models.Talukas
import com.example.agrihelp.models.Villages
import com.example.agrihelp.services.DistrictsService
import com.example.agrihelp.services.MachineService
import com.example.agrihelp.services.TalukasService
import com.example.agrihelp.services.VillagesService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.util.*


@Suppress("NAME_SHADOWING")
class BookingFragment : Fragment(), OnItemSelectedListener {

    //spinner ids
    private var machineId: Int = 0
    private var districtId: Int = 0
    private var talukaId: Int = 0
    private var villageId: Int = 0

    private lateinit var machineService: MachineService
    private lateinit var machines: Array<Machines>
    private var machineItems = mutableListOf("Select Machine")
    private lateinit var machinesSpinner: Spinner

    private lateinit var districtsService: DistrictsService
    private lateinit var districts: Array<Districts>
    private var districtItems = mutableListOf("Select District")
    private lateinit var districtsSpinner: Spinner

    private lateinit var talukaService: TalukasService
    private lateinit var talukas: Array<Talukas>
    private var talukaItems = mutableListOf("Select Taluka")
    private lateinit var talukasSpinner: Spinner

    private lateinit var villageService: VillagesService
    private lateinit var villages: Array<Villages>
    private var villageItems = mutableListOf("Select Village")
    private lateinit var villageSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    private lateinit var dateEdt: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.harvester))
        imageList.add(SlideModel(R.drawable.drone_sprayer))
        imageList.add(SlideModel(R.drawable.thresher1))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        machinesSpinner = view.findViewById(R.id.spiSelectMachinery)
        machinesSpinner.onItemSelectedListener = this

        districtsSpinner = view.findViewById(R.id.spiSelectDistrict)
        districtsSpinner.onItemSelectedListener = this

        talukasSpinner = view.findViewById(R.id.spiSelectTaluka)
        talukasSpinner.onItemSelectedListener = this

        villageSpinner = view.findViewById(R.id.spiSelectVillage)
        villageSpinner.onItemSelectedListener = this

        imageSlider.setImageList(imageList)

        showDatePicker()
        configureMachineData()
        configureDistrictData()
        if(districtId == 0)
            configureTalukasData()
        else
            configureTalukasDataByDistrictId()

        if(talukaId == 0)
           configureVillagesData()
        else
            configureVillageDataByTalukaId()

        showDatePicker()
    }

    private fun showDatePicker() {
        dateEdt = requireView().findViewById(R.id.idEdtDate)

        dateEdt.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, _, monthOfYear, dayOfMonth ->

                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dateEdt.setText(dat)
                },

                year,
                month,
                day
            )

            datePickerDialog.show()
        }

    }

    private fun configureMachineData() {
        CoroutineScope(Dispatchers.IO).launch {

            machineService = MachineService()
            val response = machineService.getMachine()

            if (response.code == HttpURLConnection.HTTP_OK) {
                machines = Gson().fromJson(response.message, Array<Machines>::class.java)
                for (machine in machines) {
                    machineItems.add(machine.machine_name)
                }

                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.spinner_list, machineItems
                    )
                    machinesSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Machines not found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun configureDistrictData() {
        CoroutineScope(Dispatchers.IO).launch {
            districtsService = DistrictsService()
            val response = districtsService.getDistricts()

            if (response.code == HttpURLConnection.HTTP_OK) {
                districts = Gson().fromJson(response.message, Array<Districts>::class.java)
                for (district in districts) {
                    districtItems.add(district.district_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.spinner_list, districtItems
                    )
                    districtsSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Districts not found", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun configureTalukasData() {
       CoroutineScope(Dispatchers.IO).launch {

            talukaService = TalukasService()
            val response = talukaService.getTalukas()

            if (response.code == HttpURLConnection.HTTP_OK) {
                talukas = Gson().fromJson(response.message, Array<Talukas>::class.java)
                for (taluka in talukas) {
                     talukaItems.add(taluka.taluka_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.spinner_list, talukaItems
                    )
                    talukasSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Talukas not found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun configureVillagesData() {
        CoroutineScope(Dispatchers.IO).launch {
            villageService = VillagesService()
            val response = villageService.getVillages()
            if (response.code == HttpURLConnection.HTTP_OK) {
                villages = Gson().fromJson(response.message, Array<Villages>::class.java)
                for (village in villages) {
                    villageItems.add(village.village_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.spinner_list, villageItems
                    )
                    villageSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Villages not found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if (parent !is Spinner)
            return

        when (parent.id) {
            R.id.spiSelectMachinery -> { machineSelected(position) }
            R.id.spiSelectDistrict -> { districtSelected(position) }
            R.id.spiSelectTaluka -> { talukaSelected(position) }
            R.id.spiSelectVillage -> { villageSelected(position) }
        }
    }

    private fun machineSelected(position: Int) {
        if (position == 0)
            return

        val selectedMachineId = machines[position - 1].id
        machineId = selectedMachineId
    }

    private fun districtSelected(position: Int) {
        if (position == 0)
            return

        val selectedDistrictId = districts[position - 1].id
        districtId = selectedDistrictId


        configureTalukasDataByDistrictId()
    }


    private fun talukaSelected(position: Int) {
        if (position == 0)
            return

        val selectedTalukaId = talukas[position - 1].id
        talukaId = selectedTalukaId

        configureVillageDataByTalukaId()
    }

    private fun villageSelected(position: Int) {
        if (position == 0)
            return

        val selectedVillageId = villages[position - 1].id
        villageId = selectedVillageId
    }
    private fun configureTalukasDataByDistrictId() {
        CoroutineScope(Dispatchers.IO).launch {
            var talukaItemsById = mutableListOf<String>()
            talukaService = TalukasService()
            val response = talukaService.getTalukasById(districtId)

            if (response.code == HttpURLConnection.HTTP_OK) {
                talukas = Gson().fromJson(response.message, Array<Talukas>::class.java)
                for (taluka in talukas) {
                    talukaItemsById.add(taluka.taluka_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.spinner_list, talukaItemsById
                    )
                    talukasSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Talukas not found", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun configureVillageDataByTalukaId() {
        CoroutineScope(Dispatchers.IO).launch {
            var villageItemByTalukaId = mutableListOf<String>()
            villageService = VillagesService()
            val response = villageService.getVillagesById(talukaId)

            if (response.code == HttpURLConnection.HTTP_OK) {
                villages = Gson().fromJson(response.message, Array<Villages>::class.java)
                for (village in villages) {
                    villageItemByTalukaId.add(village.village_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.spinner_list, villageItemByTalukaId
                    )
                    villageSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Villages not found", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
