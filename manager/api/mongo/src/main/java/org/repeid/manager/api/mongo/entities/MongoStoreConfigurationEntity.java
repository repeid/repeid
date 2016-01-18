package org.repeid.manager.api.mongo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

@Entity
@Table(name = "STORE_CONFIGURATION")
@NamedQueries(value = {
		@NamedQuery(name = "MongoStoreConfigurationEntity.findAll", query = "SELECT s FROM MongoStoreConfigurationEntity s"),
		@NamedQuery(name = "MongoStoreConfigurationEntity.findByIsDefault", query = "SELECT s FROM MongoStoreConfigurationEntity s WHERE s.isDefault =:isDefault"),
		@NamedQuery(name = "MongoStoreConfigurationEntity.findByDenominacion", query = "SELECT s FROM MongoStoreConfigurationEntity s WHERE s.denominacion = :denominacion") })
public class MongoStoreConfigurationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NotNull
	@Size(min = 1, max = 100)
	@NotBlank
	@Column(name = "provider_name")
	private String providerName;

	@NaturalId
	@NotNull
	@Size(min = 1, max = 100)
	@NotBlank
	@Column(name = "denominacion")
	private String denominacion;

	@NotNull
	@Size(min = 1, max = 200)
	@NotBlank
	@Column(name = "carpeta_raiz")
	private String carpetaRaiz;

	@NotNull
	@Size(min = 1, max = 200)
	@NotBlank
	@Column(name = "carpeta_foto")
	private String carpetaFoto;

	@NotNull
	@Size(min = 1, max = 200)
	@NotBlank
	@Column(name = "carpeta_firma")
	private String carpetaFirma;

	@NotNull
	@Size(min = 1, max = 200)
	@NotBlank
	@Column(name = "carpeta_temporal")
	private String carpetaTemporal;

	@NotNull
	@Size(min = 1, max = 200)
	@NotBlank
	@Column(name = "app_key")
	private String appKey;

	@NotNull
	@Size(min = 1, max = 1000)
	@NotBlank
	@Column(name = "app_secret")
	private String appSecret;

	@Size(min = 1, max = 1000)
	@Column(name = "token")
	private String token;

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	@Column(name = "is_default")
	private boolean isDefault;

	public MongoStoreConfigurationEntity() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getCarpetaRaiz() {
		return carpetaRaiz;
	}

	public void setCarpetaRaiz(String carpetaRaiz) {
		this.carpetaRaiz = carpetaRaiz;
	}

	public String getCarpetaFoto() {
		return carpetaFoto;
	}

	public void setCarpetaFoto(String carpetaFoto) {
		this.carpetaFoto = carpetaFoto;
	}

	public String getCarpetaFirma() {
		return carpetaFirma;
	}

	public void setCarpetaFirma(String carpetaFirma) {
		this.carpetaFirma = carpetaFirma;
	}

	public String getCarpetaTemporal() {
		return carpetaTemporal;
	}

	public void setCarpetaTemporal(String carpetaTemporal) {
		this.carpetaTemporal = carpetaTemporal;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denominacion == null) ? 0 : denominacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MongoStoreConfigurationEntity other = (MongoStoreConfigurationEntity) obj;
		if (denominacion == null) {
			if (other.denominacion != null)
				return false;
		} else if (!denominacion.equals(other.denominacion))
			return false;
		return true;
	}

}