package logica;
/**
 *
 * @author Walter
 */
public class ClientesLogica {
    //Declaración de los Miembros get and set para los Datos de la Relación cliente
    private String rtnCliente, nombre, apellido, direccion, telefono;
    private int idSexo;
     private String idSSexo;
    public String getIdSSexo() {
        return idSSexo;
    }

    public void setIdSSexo(String idSSexo) {
        this.idSSexo = idSSexo;
    }
   
    public String getRtnCliente() {
        return rtnCliente;
    }

    public void setRtnCliente(String rtnCliente) {
        this.rtnCliente = rtnCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }
    
}
