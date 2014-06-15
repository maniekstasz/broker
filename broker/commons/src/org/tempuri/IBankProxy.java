package org.tempuri;

public class IBankProxy implements org.tempuri.IBank {
  private String _endpoint = null;
  private org.tempuri.IBank iBank = null;
  
  public IBankProxy() {
    _initIBankProxy();
  }
  
  public IBankProxy(String endpoint) {
    _endpoint = endpoint;
    _initIBankProxy();
  }
  
  private void _initIBankProxy() {
    try {
      iBank = (new org.tempuri.BankLocator()).getBasicHttpBinding_IBank();
      if (iBank != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iBank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iBank)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iBank != null)
      ((javax.xml.rpc.Stub)iBank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IBank getIBank() {
    if (iBank == null)
      _initIBankProxy();
    return iBank;
  }
  
  public org.datacontract.schemas._2004._07.Bank.RegisterResult register(java.lang.String login, java.lang.String password, java.lang.String uri) throws java.rmi.RemoteException{
    if (iBank == null)
      _initIBankProxy();
    return iBank.register(login, password, uri);
  }
  
  public org.datacontract.schemas._2004._07.Bank.WireResult wireTransfer(java.lang.String login, java.lang.String password, java.lang.String banOfReceiver, java.lang.String title, java.lang.Double amount) throws java.rmi.RemoteException{
    if (iBank == null)
      _initIBankProxy();
    return iBank.wireTransfer(login, password, banOfReceiver, title, amount);
  }
  
  public org.datacontract.schemas._2004._07.Bank.Receipt getReceipt(java.lang.String login, java.lang.String password, java.lang.String id) throws java.rmi.RemoteException{
    if (iBank == null)
      _initIBankProxy();
    return iBank.getReceipt(login, password, id);
  }
  
  
}