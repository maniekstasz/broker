/**
 * IDebtCollector.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IDebtCollector extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.DebtCollectorService.DebtCollectionRegisterResult debtCollectionRegister(java.lang.String login, java.lang.String password, java.lang.Double amount, java.lang.String firstname, java.lang.String lastname, java.lang.String city, java.lang.String street, java.lang.String zip, java.lang.String homeNr) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.DebtCollectorService.RegisterResult register(java.lang.String login, java.lang.String password) throws java.rmi.RemoteException;
}
