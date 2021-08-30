package com.demidrolll.myphotos.ws.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "photos")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotosSoap {

    @XmlElement(name = "photo")
    private List<PhotoSoap> photos;

    private Long total;
}
