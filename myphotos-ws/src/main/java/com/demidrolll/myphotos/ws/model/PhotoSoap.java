package com.demidrolll.myphotos.ws.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "photo")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotoSoap {

    private ProfileSoap profile;
}
