package BusquedaPalabras_AgendaContactos;

import java.util.HashMap;
import java.util.Map;

public class AgendaContactos {
    private Map<String, Contactos> contactos;

    public AgendaContactos() {
        this.contactos = new HashMap<>();
    }

    public void agregarContacto(String nombre, String email, String telefono) {
        Contactos contacto = new Contactos(nombre, email, telefono);
        contactos.put(nombre, contacto);
    }

    public Contactos getContacto(String nombre) {
        return contactos.get(nombre);
    }

    public Map<String, Contactos> getContactos() {
        return contactos;
    }
}