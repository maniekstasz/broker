package soa.premisebroker.finance;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import soa.common.finance.Invoice;
import soa.common.finance.Invoice.InvoicePart;
import soa.premisebroker.model.Bill;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class Invoicer {

	private final String accountNr;

	public Invoicer(String accountNr) {
		this.accountNr = accountNr;
	}

	public Invoice getInvoice(Bill bill, String titlePrefix) {
		return getInvoiceFromBill(bill, titlePrefix);
	}

	public List<Invoice> getInvoices(List<Bill> bills, String titlePrefix) {
		List<Invoice> invoices = new ArrayList<Invoice>(bills.size());
		for (Bill bill : bills)
			invoices.add(getInvoice(bill, titlePrefix));
		return invoices;
	}

	private Invoice getInvoiceFromBill(Bill bill, String titlePrefix) {
		InvoicePart items[] = new InvoicePart[bill.getBillItems().size()];
		for (int i = 0; i < bill.getBillItems().size(); i++) {
			items[i] = new InvoicePart(
					bill.getBillItems().get(i).getItemName(), bill
							.getBillItems().get(i).getItemPrice());
		}
		return new Invoice(accountNr, bill.getAmount(),
				titlePrefix + " id: " + bill.getId().toString(), items);
	}

	public void writeInvoicePdf(OutputStream outputStream, Invoice invoice)
			throws DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.addTitle("faktura");
		document.addSubject("Faktura");

		document.open();

		document.add(new Paragraph("Przelac na rachunek o nr: "
				+ invoice.getAccountNr()));
		document.add(new Paragraph("Tytul przelewu(prosze nie zmiemiac): "
				+ invoice.getTitle()));
		document.add(new Paragraph("Uslugi: "));
		for (InvoicePart part : invoice.getItems())
			document.add(new Paragraph(part.getName() + "          koszt: "
					+ part.getAmount()));

		document.add(new Paragraph("Lacznie: " + invoice.getAmount()));

		document.close();
	}
}
