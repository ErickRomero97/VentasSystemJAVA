package logica;

/**
 *
 * @author Walter
 */
public class FacturaLogica {
    //Declaración de los Miembres get and set de la Clase Factura Logica
    private String fechaFactura, rtnCliente,tipoSFactura;
    private int idFactura,idProducto,idTipoFactura,idUsuario;
    private double precio,cantidad;
    private String nombreProducto;
    private String nombreCliente,apellidoCliente;
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }
    
    
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String NombreProducto) {
        this.nombreProducto = NombreProducto;
    }
   
    
    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getRtnCliente() {
        return rtnCliente;
    }

    public void setRtnCliente(String rtnCliente) {
        this.rtnCliente = rtnCliente;
    }

    public String getTipoSFactura() {
        return tipoSFactura;
    }

    public void setTipoSFactura(String tipoSFactura) {
        this.tipoSFactura = tipoSFactura;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdTipoFactura() {
        return idTipoFactura;
    }

    public void setIdTipoFactura(int idTipoFactura) {
        this.idTipoFactura = idTipoFactura;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
   
}
