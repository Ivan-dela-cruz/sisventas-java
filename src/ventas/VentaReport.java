package ventas;

public class VentaReport {

    private String nunfac;
    private String fecha;
    private String nom;
    private String ci;
    private String igv;
    private String subtotal;
    private String total;

    public VentaReport(String nunfac, String fecha, String nom, String ci, String igv, String subtotal, String total) {
        this.nunfac = nunfac;
        this.fecha = fecha;
        this.nom = nom;
        this.ci = ci;
        this.igv = igv;
        this.subtotal = subtotal;
        this.total = total;
    }
    
    

    public String getNunfac() {
        return nunfac;
    }

    public void setNunfac(String nunfac) {
        this.nunfac = nunfac;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

   

}
