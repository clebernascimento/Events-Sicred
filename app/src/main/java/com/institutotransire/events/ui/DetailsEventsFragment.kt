package com.institutotransire.events.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.institutotransire.events.R
import com.institutotransire.events.controller.DateTime
import com.institutotransire.events.controller.LoadDialog
import com.institutotransire.events.databinding.FragmentDetailsEventsBinding
import com.institutotransire.events.services.dataBase.EventsDataSource
import com.institutotransire.events.services.model.Contains
import com.institutotransire.events.services.model.DetailsEvents
import com.institutotransire.events.util.Formatters
import com.institutotransire.events.util.ImgUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class DetailsEventsFragment : Fragment(), View.OnClickListener {

    private lateinit var detailsEventsBinding: FragmentDetailsEventsBinding
    private var main: MainActivity? = null

    private var mEventsDataSource: EventsDataSource? = null
    private var loadDialogEvents: LoadDialog? = null

    private val nf = Formatters.numFormat()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        detailsEventsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_events, container, false)

        components()

        return detailsEventsBinding.getRoot()
    }

    /**
     * Metodo para inicializar os componentes
     */
    fun components() {
        main = activity as MainActivity?
        loadDialogEvents = context?.let { LoadDialog(it) }

        detailsEventsBinding.btnBackHome.setOnClickListener(this)

        setAPI()
        detailsEvents
        formChekin()
        onBackPressedFragment()
    }

    /**
     * Metodo para click
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_backHome -> goToHome()
        }
    }

    /**
     * Metodo para iniciar as API's
     */
    fun setAPI() {
        mEventsDataSource = EventsDataSource.instance
    }

    /**
     * Metodo para mostrar o evento escolhido
     */
    @get:SuppressLint("SetTextI18n")
    val detailsEvents: Unit
        get() {
            ImgUtil.requestImgWeb(context, detailsEventsBinding.imgEvents, arguments!!.getString("img"))
            detailsEventsBinding.nameEvents.text = arguments!!.getString("title")
            detailsEventsBinding.dateEvents.text = "Data: " + DateTime.getDate(arguments!!.getLong("date"))
            if (nf != null) detailsEventsBinding.priceEvents.text = "R$ " + nf.format(arguments!!.getDouble("price"))
            detailsEventsBinding.descriptionEvents.text = arguments!!.getString("desc")
        }

    /**
     * Metodo para fazer check-in
     */
    fun setChekin() {
        val name = detailsEventsBinding.editName.text.toString()
        val email = detailsEventsBinding.editEmail.text.toString()

        val bodyEvesnts = DetailsEvents(Contains.id, name, email)

        detailsEventsBinding.btnChekin.setOnClickListener { view: View? ->

            if (!validate(name, email)) return@setOnClickListener

            loadDialogEvents?.let { LoadDialog.showStandardLoading(it, "Aguade! Estamos fazendo seu check-in.") }

            mEventsDataSource!!.checkin(bodyEvesnts, object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    println("CHECK-IN REALIZADO")
                    println(response)
                    if (response.isSuccessful) {
                        activity!!.supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.container, ChekinFragment.newInstance())
                                .commit()
                    } else {
                        println("ERRO AO FAZER CHECK-IN")
                        setErrorCheckin()
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    println("ERRO DE REQUISICAO AO FAZER CHECK-IN")
                    Log.e("Error", t.message!!)
                    setErrorCheckin()
                }
            })
            loadDialogEvents!!.dismissIt()
        }
    }

    /**
     * Metodo para fragmento de erro de check-in
     */
    open fun setErrorCheckin() {
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ErroCheckinFragment.newInstance())
                .commit()
    }

    /**
     *Metodo para validar campos nome e e-mail
     */
    fun validate(name: String, email: String): Boolean {

        detailsEventsBinding.editEmail.requestFocus()
        if (email.isEmpty()) {
            errorForm()
            return false

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorForm()
            return false
        }

        detailsEventsBinding.editName.requestFocus()
        if (name.isEmpty()) {
            errorForm()
            return false

//        } else if (!name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
//            errorForm()
//            return false
        }
        return true
    }

    /**
     * Metodo para erro de Login
     */
    fun errorForm() {
        Snackbar.make(detailsEventsBinding.editName, "Ops! Campo nome ou e-mail incorreto.", Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(R.color.blue_header))
                .setTextColor(resources.getColor(R.color.white))
                .setActionTextColor(resources.getColor(R.color.white))
                .setAction("Fechar") { }
                .show()
    }

    /**
     * Metodo para validar formulario do login
     */
    fun formChekin() {
        detailsEventsBinding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                detailsEventsBinding.labelName.boxStrokeColor = resources.getColor(R.color.blue_header)
                detailsEventsBinding.editEmail.setHintTextColor(ColorStateList.valueOf(resources.getColor(R.color.blue_header)))

                if (detailsEventsBinding.editEmail.text.toString().isEmpty()) {
                    detailsEventsBinding.btnChekin.isEnabled = false

                } else {
                    detailsEventsBinding.btnChekin.isEnabled = true
                    setChekin()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        detailsEventsBinding.editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                detailsEventsBinding.labelName.boxStrokeColor = resources.getColor(R.color.blue_header)
                detailsEventsBinding.editEmail.setHintTextColor(ColorStateList.valueOf(resources.getColor(R.color.blue_header)))

                if (detailsEventsBinding.editEmail.text.toString().isEmpty()) {
                    detailsEventsBinding.btnChekin.isEnabled = false

                } else {
                    detailsEventsBinding.btnChekin.isEnabled = true
                    setChekin()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    /*Metodo para o backSpace do celular*/
    private fun onBackPressedFragment() {
        detailsEventsBinding.root.isFocusableInTouchMode = true
        detailsEventsBinding.root.requestFocus()
        detailsEventsBinding.root.setOnKeyListener { view: View?, i: Int, keyEvent: KeyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                goToHome()
                return@setOnKeyListener true
            }
            false
        }
    }

    /*Metodo para voltar*/
    private fun goToHome() {
        val fragment: Fragment = MainFragment()
        main!!.openFragment(fragment)
    }

    companion object {
        /**
         * Metodo para instaciar o fragmento
         */
        fun newInstance(): DetailsEventsFragment {
            return DetailsEventsFragment()
        }
    }
}