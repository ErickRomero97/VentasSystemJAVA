/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author Miguel
 */
public class ProductoLogica {
    private int idproducto, unidadExistencia, existenciaMinima;
    private String nombreProducto, productoCol, proveedor, idproveedor;
    private double precioVenta, precioCompra;

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(String idproveedor) {
        this.idproveedor = idproveedor;
    }    

    public int getUnidadExistencia() {
        return unidadExistencia;
    }

    public void setUnidadExistencia(int unidadExistencia) {
        this.unidadExistencia = unidadExistencia;
    }

    public int getExistenciaMinima() {
        return existenciaMinima;
    }

    public void setExistenciaMinima(int existenciaMinima) {
        this.existenciaMinima = existenciaMinima;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getProductoCol() {
        return productoCol;
    }

    public void setProductoCol(String productoCol) {
        this.productoCol = productoCol;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
