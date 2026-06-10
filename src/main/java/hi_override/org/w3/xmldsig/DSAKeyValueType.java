/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package hi_override.org.w3.xmldsig;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DSAKeyValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * <complexType name="DSAKeyValueType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <sequence minOccurs="0">
 *           <element name="P" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *           <element name="Q" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         </sequence>
 *         <element name="G" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary" minOccurs="0"/>
 *         <element name="Y" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         <element name="J" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary" minOccurs="0"/>
 *         <sequence minOccurs="0">
 *           <element name="Seed" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *           <element name="PgenCounter" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/>
 *         </sequence>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSAKeyValueType", propOrder = {
    "p",
    "q",
    "g",
    "y",
    "j",
    "seed",
    "pgenCounter"
})
public class DSAKeyValueType {

    @XmlElement(name = "P")
    protected byte[] p;
    @XmlElement(name = "Q")
    protected byte[] q;
    @XmlElement(name = "G")
    protected byte[] g;
    @XmlElement(name = "Y", required = true)
    protected byte[] y;
    @XmlElement(name = "J")
    protected byte[] j;
    @XmlElement(name = "Seed")
    protected byte[] seed;
    @XmlElement(name = "PgenCounter")
    protected byte[] pgenCounter;

    /**
     * Gets the value of the p property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getP() {
        return p;
    }

    /**
     * Sets the value of the p property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setP(byte[] value) {
        this.p = value;
    }

    /**
     * Gets the value of the q property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getQ() {
        return q;
    }

    /**
     * Sets the value of the q property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setQ(byte[] value) {
        this.q = value;
    }

    /**
     * Gets the value of the g property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getG() {
        return g;
    }

    /**
     * Sets the value of the g property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setG(byte[] value) {
        this.g = value;
    }

    /**
     * Gets the value of the y property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setY(byte[] value) {
        this.y = value;
    }

    /**
     * Gets the value of the j property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getJ() {
        return j;
    }

    /**
     * Sets the value of the j property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setJ(byte[] value) {
        this.j = value;
    }

    /**
     * Gets the value of the seed property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSeed() {
        return seed;
    }

    /**
     * Sets the value of the seed property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSeed(byte[] value) {
        this.seed = value;
    }

    /**
     * Gets the value of the pgenCounter property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPgenCounter() {
        return pgenCounter;
    }

    /**
     * Sets the value of the pgenCounter property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPgenCounter(byte[] value) {
        this.pgenCounter = value;
    }

}
