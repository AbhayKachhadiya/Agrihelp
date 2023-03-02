package com.example.agrihelp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.agrihelp.models.*
import com.example.agrihelp.services.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NAME_SHADOWING")
class BookingFragment : Fragment(), OnItemSelectedListener {

    //spinner ids
    private var machineId: Int = 0
    private var districtId: Int = 0
    private var talukaId: Int = 0
    private var villageId: Int = 0
    private var operatorId: Int = 0
    private var userId : Int = 0

    //Get data into Spinner
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

    private lateinit var operatorService: OperatorService
    private lateinit var operators: Array<Operators>
    private var operatorItems = mutableListOf("Select Operator")
    private lateinit var operatorSpinner: Spinner

    //Service Booking
    private lateinit var serviceBookingService: ServiceBookingService
    private lateinit var serviceBooking: ServiceBooking
    private lateinit var txtName: EditText
    private lateinit var txtPincode: EditText
    private lateinit var txtMobileNumber: EditText
    private lateinit var txtCropName: EditText
    private lateinit var txtHours: EditText
    private lateinit var dateEdt: TextView
    private lateinit var bookingDate: String
    private lateinit var btnBooking: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_booking, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = this.requireActivity().getSharedPreferences("agrihelp", AppCompatActivity.MODE_PRIVATE)
        userId = sharedPrefs.getInt("id",0)

        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.harvester))
        imageList.add(SlideModel(R.drawable.drone_sprayer))
        imageList.add(SlideModel(R.drawable.thresher1))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)

        //SpinnerData
        machinesSpinner = view.findViewById(R.id.spiSelectMachinery)
        machinesSpinner.onItemSelectedListener = this

        districtsSpinner = view.findViewById(R.id.spiSelectDistrict)
        districtsSpinner.onItemSelectedListener = this

        talukasSpinner = view.findViewById(R.id.spiSelectTaluka)
        talukasSpinner.onItemSelectedListener = this

        villageSpinner = view.findViewById(R.id.spiSelectVillage)
        villageSpinner.onItemSelectedListener = this

        operatorSpinner = view.findViewById(R.id.spiSelectOperator)
        operatorSpinner.onItemSelectedListener = this

        //BookingData
        txtName = view.findViewById(R.id.txtFullName)
        txtPincode = view.findViewById(R.id.txtPincode)
        txtHours = view.findViewById(R.id.txtHours)
        txtMobileNumber = view.findViewById(R.id.txtPhoneNumber)
        txtCropName = view.findViewById(R.id.txtQuery)
        dateEdt = view.findViewById(R.id.idEdtDate)
        btnBooking = view.findViewById(R.id.btnSubmit)

        btnBooking.setOnClickListener {

            val id = userId.toString().toInt()
            val machine = machineId
            val name = txtName.text.toString()
            val village = villageId
            val operator = operatorId
            val pincode = txtPincode.text.toString().toInt()
            val mobileNumber = txtMobileNumber.text.toString()
            val cropName = txtCropName.text.toString()
            val bookingDate = bookingDate
            val hours = txtHours.text.toString().toInt()


            serviceBooking = ServiceBooking(
                user_id = id,
                service_id = machine,
                name = name,
                village_id = village,
                operator_id = operator,
                pincode = pincode,
                mobile_no = mobileNumber,
                crop_name = cropName,
                date = bookingDate,
                hours = hours
            )
            serviceBooking()
        }


        imageSlider.setImageList(imageList)

        showDatePicker()
        configureMachineData()
        configureDistrictData()
        if (districtId == 0) configureTalukasData()
        else configureTalukasDataByDistrictId()

        if (talukaId == 0) configureVillagesData()
        else configureVillageDataByTalukaId()

        showDatePicker()
    }

    private fun showDatePicker() {

        dateEdt.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, calendarYear, monthOfYear, dayOfMonth ->

                    val calendar = Calendar.getInstance()
                    calendar.set(calendarYear, monthOfYear, dayOfMonth)

                    val date = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                    val dateForDb = SimpleDateFormat("yyyy/MM/dd", Locale.US)
                    dateEdt.text = date.format(calendar.time)
                    bookingDate = dateForDb.format(calendar.time)
                },

                year, month, day
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
                        requireActivity(), R.layout.spinner_list, machineItems
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
                        requireActivity(), R.layout.spinner_list, districtItems
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
                        requireActivity(), R.layout.spinner_list, talukaItems
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
                        requireActivity(), R.layout.spinner_list, villageItems
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


    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if (parent !is Spinner) return

        when (parent.id) {
            R.id.spiSelectMachinery -> {
                machineSelected(position)
            }
            R.id.spiSelectDistrict -> {
                districtSelected(position)
            }
            R.id.spiSelectTaluka -> {
                talukaSelected(position)
            }
            R.id.spiSelectVillage -> {
                villageSelected(position)
            }
            R.id.spiSelectOperator ->{
                operatorSelected(position)
            }
        }
    }

    private fun machineSelected(position: Int) {
        if (position == 0) return

        val selectedMachineId = machines[position - 1].id
        machineId = selectedMachineId
    }

    private fun districtSelected(position: Int) {
        if (position == 0) return

        val selectedDistrictId = districts[position - 1].id
        districtId = selectedDistrictId


        configureTalukasDataByDistrictId()
    }


    private fun talukaSelected(position: Int) {
        if (position == 0) return

        val selectedTalukaId = talukas[position - 1].id
        talukaId = selectedTalukaId

        configureVillageDataByTalukaId()
        configureOperatorDataByTalukaIdAndMachineId()
    }

    private fun villageSelected(position: Int) {
        if (position == 0) return

        val selectedVillageId = villages[position - 1].id
        villageId = selectedVillageId
    }

    private fun operatorSelected(position: Int) {
        if (position == 0) return

        val selectedOperatorId = operators[position - 1].id
        operatorId = selectedOperatorId
    }
    private fun configureTalukasDataByDistrictId() {
        CoroutineScope(Dispatchers.IO).launch {
            val talukaItemsById = mutableListOf("Select Taluka")
            talukaService = TalukasService()
            val response = talukaService.getTalukasById(districtId)

            if (response.code == HttpURLConnection.HTTP_OK) {
                talukas = Gson().fromJson(response.message, Array<Talukas>::class.java)
                for (taluka in talukas) {
                    talukaItemsById.add(taluka.taluka_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(), R.layout.spinner_list, talukaItemsById
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
            val villageItemByTalukaId = mutableListOf("Select Village")
            villageService = VillagesService()
            val response = villageService.getVillagesById(talukaId)

            if (response.code == HttpURLConnection.HTTP_OK) {
                villages = Gson().fromJson(response.message, Array<Villages>::class.java)
                for (village in villages) {
                    villageItemByTalukaId.add(village.village_name)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(), R.layout.spinner_list, villageItemByTalukaId
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

    private fun configureOperatorDataByTalukaIdAndMachineId() {
        CoroutineScope(Dispatchers.IO).launch {
            val operatorDataByTalukaIdAndMachineId = mutableListOf("Select Operator")
            operatorService = OperatorService()
            val response = operatorService.getOperatorById(talukaId,machineId)

            if (response.code == HttpURLConnection.HTTP_OK) {
                operators = Gson().fromJson(response.message, Array<Operators>::class.java)
                for (operator in operators) {
                    operatorDataByTalukaIdAndMachineId.add(operator.emailaddress)
                }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireActivity(), R.layout.spinner_list, operatorDataByTalukaIdAndMachineId
                    )
                    operatorSpinner.adapter = adapter
                }
            } else if (response.code == HttpURLConnection.HTTP_NOT_FOUND) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Villages not found", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    private fun serviceBooking() {
        CoroutineScope(Dispatchers.IO).launch {
            serviceBookingService = ServiceBookingService()

            val response = serviceBookingService.serviceBooking(serviceBooking)
            if(response.code == HttpURLConnection.HTTP_CREATED) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Your booking details has sent to operator",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }

    }
    }

