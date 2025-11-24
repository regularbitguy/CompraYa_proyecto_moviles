import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovilesproy.Mensaje
import com.example.appmovilesproy.databinding.ItemMensajeEnviadoBinding
import com.example.appmovilesproy.databinding.ItemMensajeRecibidoBinding

class MensajeAdapter(private val currentUserId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mensajes: List<Mensaje> = emptyList()

    fun submitList(nuevaLista: List<Mensaje>) {
        mensajes = nuevaLista
        notifyDataSetChanged()
    }

    private val TIPO_ENVIADO = 1
    private val TIPO_RECIBIDO = 2

    override fun getItemViewType(position: Int): Int {
        return if (mensajes[position].autorId == currentUserId) TIPO_ENVIADO else TIPO_RECIBIDO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TIPO_ENVIADO) {
            val binding = ItemMensajeEnviadoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            EnviadoViewHolder(binding)
        } else {
            val binding = ItemMensajeRecibidoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            RecibidoViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = mensajes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mensaje = mensajes[position]
        if (holder is EnviadoViewHolder) holder.bind(mensaje)
        else if (holder is RecibidoViewHolder) holder.bind(mensaje)
    }

    inner class EnviadoViewHolder(private val binding: ItemMensajeEnviadoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(m: Mensaje) {
            binding.txtMensajeEnviado.text = m.contenido
        }
    }

    inner class RecibidoViewHolder(private val binding: ItemMensajeRecibidoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(m: Mensaje) {
            binding.txtMensajeRecibido.text = m.contenido
        }
    }
}
