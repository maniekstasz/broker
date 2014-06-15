package org.tempuri;

public class IDebtCollectorProxy implements org.tempuri.IDebtCollector {
  private String _endpoint = null;
  private org.tempuri.IDebtCollector iDebtCollector = null;
  
  public IDebtCollectorProxy() {
    _initIDebtCollectorProxy();
  }
  
  public IDebtCollectorProxy(String endpoint) {
    _endpoint = endpoint;
    _initIDebtCollectorProxy();
  }
  
  private void _initIDebtCollectorProxy() {
    try {
      iDebtCollector = (new org.tempuri.DebtCollectorLocator()).getBasicHttpBinding_IDebtCollector();
      if (iDebtCollector != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iDebtCollector)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iDebtCollector)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iDebtCollector != null)
      ((javax.xml.rpc.Stub)iDebtCollector)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IDebtCollector getIDebtCollector() {
    if (iDebtCollector == null)
      _initIDebtCollectorProxy();
    return iDebtCollector;
  }
  
  public org.datacontract.schemas._2004._07.DebtCollectorService.DebtCollectionRegisterResult debtCollectionRegister(java.lang.String login, java.lang.String password, java.lang.Double amount, java.lang.String firstname, java.lang.String lastname, java.lang.String city, java.lang.String street, java.lang.String zip, java.lang.String homeNr) throws java.rmi.RemoteException{
    if (iDebtCollector == null)
      _initIDebtCollectorProxy();
    return iDebtCollector.debtCollectionRegister(login, password, amount, firstname, lastname, city, street, zip, homeNr);
  }
  
  public org.datacontract.schemas._2004._07.DebtCollectorService.RegisterResult register(java.lang.String login, java.lang.String password) throws java.rmi.RemoteException{
    if (iDebtCollector == null)
      _initIDebtCollectorProxy();
    return iDebtCollector.register(login, password);
  }
  
  
}