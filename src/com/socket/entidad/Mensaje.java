package com.socket.entidad;

import java.io.Serializable;

public class Mensaje implements Serializable{
	private String ipOrigen;
	private String nombre;
	private String hora;
	private String pregunta;
	private String respuesta;
	private String accion;
	private byte[] archivo;
	private String nombreArchivo;
	private String direccionDestino;
	
	public String getIpOrigen() {
		return ipOrigen;
	}
	public void setIpOrigen(String ipOrigen) {
		this.ipOrigen = ipOrigen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getDireccionDestino() {
		return direccionDestino;
	}
	public void setDireccionDestino(String direccionDestino) {
		this.direccionDestino = direccionDestino;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}	
	
}



