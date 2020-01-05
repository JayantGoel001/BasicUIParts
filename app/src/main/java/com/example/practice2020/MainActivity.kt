package com.example.practice2020

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ClipboardManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.PopupMenu
import androidx.appcompat.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.customlayout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    @SuppressLint("NewApi", "ObsoleteSdkInt", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val list:Array<String> = arrayOf("CUPCAKE","DONUT","ECLAIRS","FROYO","GINGERBREAD","HONEYCOMB","ICE CREAM-SANDWICH","JELLYBEAN","KIT KAT"
            ,"LOLLIPOP","MARSHMALLOW","NOUGAT","OREO","PIE","Q")
        val adapter=ArrayAdapter(this,android.R.layout.select_dialog_singlechoice,list)
        textview.threshold=0
        textview.setAdapter(adapter)


        button1.setOnClickListener {
            val animtrans=AnimationUtils.loadAnimation(this,R.anim.translate)
            it.startAnimation(animtrans)
        }
        button4.setOnClickListener {
            val animalpha=AnimationUtils.loadAnimation(this,R.anim.alpha)
            it.startAnimation(animalpha)
        }
        button2.setOnClickListener {
            val animscale=AnimationUtils.loadAnimation(this,R.anim.scale)
            it.startAnimation(animscale)
        }
        button3.setOnClickListener {
            val animrotate=AnimationUtils.loadAnimation(this,R.anim.rotate)
            it.startAnimation(animrotate)
        }
        button1.setOnLongClickListener {
            Toast.makeText(this,"button1",Toast.LENGTH_SHORT).show()
            true
        }
        button2.setOnLongClickListener {
            Toast.makeText(this,"button2",Toast.LENGTH_SHORT).show()
            true
        }
        button3.setOnLongClickListener {
            Toast.makeText(this,"button3",Toast.LENGTH_SHORT).show()
            true
        }
        button4.setOnLongClickListener {
            Toast.makeText(this,"button4",Toast.LENGTH_SHORT).show()
            true
        }
        radiogroup.setOnCheckedChangeListener { _, _ ->
            Toast.makeText(this,findViewById<RadioButton>(radiogroup.checkedRadioButtonId).text.toString(),Toast.LENGTH_SHORT).show()
        }
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                linearLayout.setBackgroundColor(Color.parseColor("#FF63D486"))
            } else {
                linearLayout.setBackgroundColor(Color.parseColor("#FFFDFBFB"))
            }
        }
        progressStart.setOnClickListener {
            var progressStatus:Int=0
            GlobalScope.launch(Dispatchers.IO) {
                while (progressStatus<100)
                {
                    progressStatus+=1
                    delay(20)
                    GlobalScope.launch(Dispatchers.Main) {
                        progressBar.progress = progressStatus
                        progressTextView.text=progressStatus.toString()
                    }
                }
            }
            val view=LayoutInflater.from(this).inflate(R.layout.customlayout,null)
            val toast=Toast(this)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.duration=Toast.LENGTH_SHORT
            toast.view=view
            toast.show()
        }
        seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textPr.textSize=(progress/5+20).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Snackbar.make(linearLayout,"Started Tracking",Snackbar.LENGTH_INDEFINITE).setAction("OK"
                ) {
                    Toast.makeText(this@MainActivity,"Started Tracking",Toast.LENGTH_SHORT).show()
                }.show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity,"Stopped Tracking",Toast.LENGTH_SHORT).show()
            }
        }
        )
//        val myAdapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayOf("Sunday",
//            "Monday",
//            "Tuesday",
//            "Wednesday",
//            "Thursday",
//            "Friday",
//            "Saturday"))
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter=myAdapter
        var c=0
        alertDialog.setOnClickListener {
            c++
            AlertDialog.Builder(this)
                .setTitle("Alert Dialog")
                .setMessage("Do wants to Change Theme of Button ?")
                .setCancelable(false)
                .setPositiveButton("YES"
                ) { _, _ ->
                    when {
                        c%4==0 -> {
                            alertDialog.setBackgroundResource(R.drawable.roundcorner)
                        }
                        c%4==1 -> {
                            alertDialog.setBackgroundResource(R.drawable.circle)
                        }
                        c%4==2 -> {
                            alertDialog.setBackgroundResource(R.drawable.rectangle)
                        }
                        c%4==3 -> {
                            alertDialog.setBackgroundResource(R.drawable.oval)
                        }
                    }
                }.setNegativeButton("NO"
                ) { _, _ ->
                }
                .show()
        }
        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            Toast.makeText(this, "Time $hourOfDay:$minute",Toast.LENGTH_SHORT).show()
        }
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            Toast.makeText(this, "Date $dayOfMonth/${monthOfYear +1}/$year",Toast.LENGTH_SHORT).show()
        }
        copy.setOnClickListener {
            val copyText=copyText.text.toString()
            if(copyText.isNotEmpty())
            {
                if(android.os.Build.VERSION.SDK_INT<android.os.Build.VERSION_CODES.HONEYCOMB)
                {
                    val clipboardManager=getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
                    clipboardManager.text=copyText
                    Toast.makeText(this,"Text Copied To Clipboard.",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val clipboardManager: ClipboardManager=getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip=android.content.ClipData.newPlainText("Clip",copyText)
                    Toast.makeText(this,"Text Copied To Clipboard.",Toast.LENGTH_SHORT).show()
                    clipboardManager.setPrimaryClip(clip)
                }
            }
            else
            {
                Toast.makeText(this,"Nothing To Copy.",Toast.LENGTH_SHORT).show()
            }
        }

        paste.setOnClickListener {
            if(android.os.Build.VERSION.SDK_INT<android.os.Build.VERSION_CODES.HONEYCOMB)
            {
                val clipboard=getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
                pasteText.text=clipboard.text.toString()
            }
            else {
                val clipboardManager: ClipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                if(clipboardManager.hasPrimaryClip())
                {
                    val item=clipboardManager.primaryClip!!.getItemAt(0)
                    pasteText.text=item.text.toString()
                }
                else
                {
                    Toast.makeText(this,"Nothing To Paste",Toast.LENGTH_SHORT).show()
                }
            }
        }
        registerForContextMenu(contextMenu)
        pop_menu.setOnClickListener {
            val popMenu=PopupMenu(this,pop_menu)
            popMenu.menuInflater.inflate(R.menu.contextmenu,popMenu.menu)
            popMenu.setOnMenuItemClickListener {
                Toast.makeText(this,it.title.toString(),Toast.LENGTH_SHORT).show()
                true
            }
            popMenu.show()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        if(v.id==R.id.contextMenu)
        {
            createMenu(R.menu.contextmenu,menu,"Choose a Color")
        }
        else {
            super.onCreateContextMenu(menu, v, menuInfo)
        }
    }
    fun createMenu(menuId:Int, menu:ContextMenu, title:String)
    {
        this.menuInflater.inflate(menuId,menu)
        menu.setHeaderTitle(title)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_grey -> {
                contextMenu.setBackgroundResource(R.color.material_grey)
                return true
            }
            R.id.menu_teal -> {
                contextMenu.setBackgroundResource(R.color.material_teal)
                return true
            }
            R.id.menu_white -> {
                contextMenu.setBackgroundResource(R.color.material_white)
                return true
            }
            else -> {
                return super.onContextItemSelected(item)
            }
        }
    }

}
