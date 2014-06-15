/**
 * IBank.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IBank extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.Bank.RegisterResult register(java.lang.String login, java.lang.String password, java.lang.String uri) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.Bank.WireResult wireTransfer(java.lang.String login, java.lang.String password, java.lang.String banOfReceiver, java.lang.String title, java.lang.Double amount) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.Bank.Receipt getReceipt(java.lang.String login, java.lang.String password, java.lang.String id) throws java.rmi.RemoteException;
}
