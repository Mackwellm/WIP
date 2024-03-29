package com.example.wip

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wip.databinding.ActivityAddTaskBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var database: DatabaseReference
    private lateinit var MinGoal: SeekBar
    private lateinit var MaxGoal: SeekBar
    private lateinit var addImage: Button
    private lateinit var addTask: Button
    private lateinit var showData: Button



    private var image: Uri = Uri.EMPTY
    private val PICK_IMAGE_REQUEST = 1
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImageUri = result.data?.data

                if (selectedImageUri != null) {
                    image = selectedImageUri
                }

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance()
        database = db.getReference().child("Tasks")

        addTask = findViewById(R.id.btn_ADD)

        addTask.setOnClickListener {

            val name = binding.etTaskName.text.toString()
            val description = binding.etDescription.text.toString()
            val start = binding.etStartDate.text.toString()
            val end = binding.etEndDate.text.toString()
            val Hrs = binding.HrsWorkedDaily.text.toString()
            val Mini = binding.tvMin.text.toString()
            val Maxi = binding.tvMax.text.toString()

            val TaskMap = hashMapOf<String, String>()
            TaskMap.put("Task Name", name)
            TaskMap.put("Description", description)
            TaskMap.put("Start Date", start)
            TaskMap.put("End Date", end)
            TaskMap.put("Hoursworked daily so far", Hrs)
            TaskMap.put("Minimum Daily Hours", Mini)
            TaskMap.put("Maximum Daily Hours", Maxi)

            database.push().setValue(TaskMap).addOnCompleteListener {
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
            }
        }

        showData = findViewById(R.id.btn_update)

        showData.setOnClickListener {
            val intent = Intent(this, ListOfTasks::class.java)
//            intent.putExtra("Task Name", name)
//            intent.putExtra("Description", description)
//            intent.putExtra("Start Date", start)
//            intent.putExtra("End Date", end)
//            intent.putExtra("Hoursworked daily so far", Hrs)
//            intent.putExtra("Minimum Daily Hours", Mini)
//            intent.putExtra("Maximum Daily Hours", Maxi)
            startActivity(intent)
        }

        addImage = findViewById(R.id.addImage)

        addImage.setOnClickListener {
            val intent = Intent(this, AddImage::class.java)
            startActivity(intent)
        }

        //Min seekbar
        MinGoal?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            //when progress of the seekbar is changed
            override fun onProgressChanged(seek: SeekBar, progress: Int, FromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

                val minTxt = findViewById<TextView>(R.id.tvMin)
                minTxt.setText("Min Daily Goal: " + seek.progress)
            }
        })

        //Max seekbar
        MaxGoal?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            //when progress of the seekbar is changed
            override fun onProgressChanged(
                Barseek: SeekBar,
                progress: Int,
                FromUser: Boolean
            ) {

            }

            override fun onStartTrackingTouch(Barseek: SeekBar) {

            }

            override fun onStopTrackingTouch(Barseek: SeekBar) {

                val maxTxt = findViewById<TextView>(R.id.tvMax)
                maxTxt.setText("Max Daily Goal: " + Barseek.progress)
            }
        })



    }
}
//private fun pickImage() {
//    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//    intent.type = "image/*"
//    pickImageLauncher.lauch(intent)
//}


