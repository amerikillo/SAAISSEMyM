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
public class CorreoGeneraRemision {

    public void enviaCorreoFolio(String folio) {
        try {

            ConectionDB con = new ConectionDB();
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("elideth.martinez@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("oscar.arellano@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("vicente.flores@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("estevan.vences@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("javier.calero@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("mario.garcia@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo
            //message.addRecipient(Message.RecipientType.TO,new InternetAddress("joseluis.chavez@gnkl.mx"));//Aqui se pone la direccion a donde se enviara el correo*/
            message.setSubject("Generación de Remisión / GNK Logística");
            System.out.println("Generación de Remisión / GNK Logística");
            String mensaje = "Folio Nuevo\nSe acaba de generar la siguiente Remisión: " + folio + "\n";
            try {
                con.conectar();
                ResultSet rset = con.consulta("SELECT * from facturas where F_ClaDoc = '" + folio + "' GROUP BY F_ClaDoc;");
                while (rset.next()) {
                    mensaje = mensaje + "Distribuidor: " + rset.getString("F_NomCli") + "\n"
                            + "Fecha de Entrega: " + rset.getString("F_FecEnt") + "\n";
                }
                con.cierraConexion();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            mensaje = mensaje + "Clave\t\t\tLote\t\t\tCaducidad\t\t\tCantidad\n";
            try {
                con.conectar();
                ResultSet rset = con.consulta("SELECT * from facturas where F_ClaDoc = '" + folio + "';");
                while (rset.next()) {
                    mensaje = mensaje + rset.getString("F_ClaPro") + "\t\t" + rset.getString("F_ClaLot") + "\t\t" + rset.getString("F_FecCad") + "\t\t" + rset.getString("F_CantSur") + "\n";
                }
                con.cierraConexion();
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
