package com.institutotransire.events.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.institutotransire.events.R
import com.institutotransire.events.adapter.EventsAdapter
import com.institutotransire.events.adapter.EventsAdapter.OnClick
import com.institutotransire.events.controller.LoadDialog
import com.institutotransire.events.databinding.FragmentMainBinding
import com.institutotransire.events.services.dataBase.EventsDataSource
import com.institutotransire.events.services.model.Contains
import com.institutotransire.events.services.model.Events
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var mainBinding: FragmentMainBinding
    private var main: MainActivity? = null

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var eventsArrayList = ArrayList<Events>()

    private var mEventsDataSource: EventsDataSource? = null
    private var loadDialogEvents: LoadDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        components()

        return mainBinding.getRoot()
    }

    /**
     * Metodo para inicializar os componentes
     */
    fun components() {
        main = activity as MainActivity?
        loadDialogEvents = LoadDialog(context!!)

        mainBinding!!.btnTryAgain.setOnClickListener(this)

        setAPI()
        setmRecyclerView()
        onBackPressedFragment()
        eventsApi
    }

    /**
     * Metodo para Click
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnTryAgain -> eventsApi
        }
    }

    /**
     * Metodo para iniciar as API's
     */
    fun setAPI() {
        mEventsDataSource = EventsDataSource.instance
    }

    /**
     * Metodo para setar o RecyclerView
     */
    fun setmRecyclerView() {
        mainBinding.recyclerEvents.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = EventsAdapter(context!!, eventsArrayList, object : OnClick {

            override fun selectEvents(events: Events?, position: Int) {
                Log.d("Click on events", events.toString())
                val fragment = DetailsEventsFragment()

                val (_, title, date, image, description, _, _, price) = eventsArrayList[position]

                val bundle = Bundle()
                bundle.putString("img", image)
                bundle.putString("title", title)
                bundle.putLong("date", date)
                bundle.putDouble("price", price!!)
                bundle.putString("desc", description)

                fragment.arguments = bundle

                eventsArrayList.clear()

                activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit()
            }
        })
        mainBinding.recyclerEvents.layoutManager = mLayoutManager
        mainBinding.recyclerEvents.adapter = mAdapter
    }

    /**
     * Metodo para listar os eventos
     */
    val eventsApi: Unit
        get() {

            loadDialogEvents?.let { LoadDialog.showStandardLoading(it, "Aguarde! Buscando seus eventos") }

            mEventsDataSource?.listEvents(object : Callback<List<Events>?> {
                override fun onResponse(
                        call: Call<List<Events>?>,
                        response: Response<List<Events>?>
                ) {
                    println("LISTA DE EVENTOS")
                    println(response)
                    if (response.isSuccessful && response.body() != null) {
                        eventsArrayList.addAll(response.body()!!)
                        mAdapter!!.notifyDataSetChanged()
                        mainBinding.layoutListEvents.visibility = View.VISIBLE
                        mainBinding.layoutErroEvents.visibility = View.GONE

                    } else {
                        println("ERRO NA LISTAGEM DE SERVICOS")
                        println(response)
                        mainBinding.layoutListEvents.visibility = View.GONE
                        mainBinding.layoutErroEvents.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<List<Events>?>, t: Throwable) {
                    println("ERRO NA REQUISICAO DOS EVENTOS")
                    Log.d("ErrorBack-End", t.message!!)
                    mainBinding.layoutListEvents.visibility = View.GONE
                    mainBinding.layoutErroEvents.visibility = View.VISIBLE

                }
            })
            loadDialogEvents?.dismissIt()
        }

    /*Metodo para o backPressed do dispositivo*/
    private fun onBackPressedFragment() {
        mainBinding.root.isFocusableInTouchMode = true
        mainBinding.root.requestFocus()
        mainBinding.root.setOnKeyListener { view, i, keyEvent ->
            if (i === KeyEvent.KEYCODE_BACK && keyEvent.action === KeyEvent.ACTION_UP) {
                goToHome()
                return@setOnKeyListener true
            }
            false
        }
    }

    /*Metodo para voltar*/
    private fun goToHome() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Sair do aplicativo")
        builder.setMessage("Deseja sair do aplicativo?")
        builder.setCancelable(false)
        builder.setPositiveButton("Sim") { dialog: DialogInterface?, which: Int -> activity!!.finishAffinity() }.setNegativeButton("Não", null)
        builder.show()
    }

    companion object {
        /**
         * Metodo para instanciar o fragmento
         *
         * @return
         */
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}