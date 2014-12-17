/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Correo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import conn.*;
import java.sql.ResultSet;

/**
 *
 * @author Americo
 */
public class CorreoGeneraFolio {

    private String cuenta_correo;
    private String nombre;
    private String comentario;
    private String qry;
    ConectionDB obj = new ConectionDB();

    public void enviaCorreoFolio(String folio) {
        try {
            /* TODO output your page here. You may use following sample code. */
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "ricardo.wence@gnkl.mx");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("ricardo.wence@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("anibal.rincon@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("americo.guzman@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("omar_23sh@hotmail.com"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("yolanda.orozco@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("oscar.arellano@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("vicente.flores@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("javier.calero@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("mario.garcia@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("joseluis.chavez@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.setSubject("Generación de Folio / GNK Logística");
            System.out.println("Generación de Folio / GNK Logística");
            String mensaje = "Folio Nuevo\nSe acaba de generar el siguiente folio para remisión: " + folio + "\n";
            try{
                obj.conectar();
                ResultSet rset = obj.consulta("select p.F_FecSur, p.F_HorSur, pro.F_NomPro, u.F_Usuario from tb_pedidoisem p, tb_proveedor pro, tb_usuariosisem u where u.F_IdUsu = p.F_IdUsu and p.F_Provee = pro.F_ClaProve and  F_NoCompra = '" + folio + "' group by pro.F_NomPro ");
                while (rset.next()) {
                    mensaje = mensaje + "Proveedor: " + rset.getString(3) + "\n"
                            + "Fecha de Entrega: " + rset.getString(1) + " " + rset.getString(2) + "\n"
                            + "Orden capurada por: " + rset.getString(4) + "\n";
                }
                obj.cierraConexion();
            } catch (Exception e) {
            }
            mensaje = mensaje + "Clave\t\t\tCantidad\n";
            try {
                obj.conectar();
                ResultSet rset = obj.consulta("select F_Clave, F_Cant, F_Obser from tb_pedidoisem where F_NoCompra = '" + folio + "' ");
                while (rset.next()) {
                    mensaje = mensaje + rset.getString(1) + "\t\t" + rset.getString(2) + "\t\t" + rset.getString(3) + "\n";
                }
                obj.cierraConexion();
            } catch (Exception e) {
            }
            System.out.println(mensaje);
            message.setText(mensaje);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("ricardo.wence@gnkl.mx", "ricardo.wence+111");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
