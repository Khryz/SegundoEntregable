package com.wizeline.notificacion;

import com.wizeline.exceptions.ExcepcionGenerica;

public class NotificacionFactory {

    public Notifications usarNotificacion(String canal){
        switch (canal){
            case "CREAR":
                return new CrearUsuariosNotificacionImpl();
            case "LOGIN":
                return new LoginUsusarioNotificacionImpl();
            default:
                throw new ExcepcionGenerica("Canal desconocido");
        }
    }
}
