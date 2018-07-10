package com.valework.yingul.logistic;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_Product;
import com.valework.yingul.model.Yng_Quote;
import com.valework.yingul.model.Yng_Shipping;
import com.valework.yingul.model.Yng_Standard;
import com.valework.yingul.service.StandardService;

public class FedexXML {
	Yng_Standard standard;
	private static String FedEXAuthenticationKey ;
	private  String FedExMeterNumber;
	private  String FedExAccountNumber;
	private  String FedexPassword;
	@Autowired
	StandardService standardService;
	
	public String FedexRate(Yng_Quote quo, Yng_Product getProductByIdItem) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ") {
	        public StringBuffer format(Date date, StringBuffer toAppendTo, java.text.FieldPosition pos) {
	            StringBuffer toFix = super.format(date, toAppendTo, pos);
	            return toFix.insert(toFix.length()-2, ':');
	        };
	    };
	    // Usage:
	    String fechaA=dateFormat.format(new Date());
	   // System.out.println("fecha:"+fechaA);
    	
    	return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
				" <soapenv:Body>\r\n" + 
				"  <RateRequest xmlns=\"http://fedex.com/ws/rate/v22\">\r\n" + 
				"   <WebAuthenticationDetail>\r\n" + 
				"    <UserCredential>\r\n" + 
				"     <Key>"
				+ FedEXAuthenticationKey
				+ "</Key>\r\n" + 
				"     <Password>"
				+ FedexPassword
				+ "</Password>\r\n" + 
				"    </UserCredential>\r\n" + 
				"   </WebAuthenticationDetail>\r\n" + 
				"   <ClientDetail>\r\n" + 
				"    <AccountNumber>"
				+ FedExAccountNumber
				+ "</AccountNumber>\r\n" + 
				"    <MeterNumber>"
				+ FedExMeterNumber
				+ "</MeterNumber>\r\n" + 
				"   </ClientDetail>\r\n" + 
				"   <TransactionDetail>\r\n" + 
				"    <CustomerTransactionId>java sample - Rate Request</CustomerTransactionId>\r\n" + 
				"   </TransactionDetail>\r\n" + 
				"   <Version>\r\n" + 
				"    <ServiceId>crs</ServiceId>\r\n" + 
				"    <Major>22</Major>\r\n" + 
				"    <Intermediate>0</Intermediate>\r\n" + 
				"    <Minor>0</Minor>\r\n" + 
				"   </Version>\r\n" + 
				"   <ReturnTransitAndCommit>true</ReturnTransitAndCommit>\r\n" + 
				"   <RequestedShipment>\r\n" + 
				"    <ShipTimestamp>"
				+fechaA
				+ "</ShipTimestamp>\r\n" + 
				"    <DropoffType>"
				+ "REGULAR_PICKUP"
				+ "</DropoffType>\r\n" + 
				"    <Shipper>\r\n" + 
				"     <Address>\r\n" + 
				"      <StreetLines>"
				+ quo.getYng_Item().getYng_Ubication().getStreet()
				+ "</StreetLines>\r\n" + 
				"      <PostalCode>"
				+ quo.getYng_Item().getYng_Ubication().getPostalCode()
				+ "</PostalCode>\r\n" + 
				"      <CountryCode>"
				+ quo.getYng_Item().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"     </Address>\r\n" + 
				"    </Shipper>\r\n" + 
				"    <Recipient>\r\n" + 
				"     <Address>\r\n" + 
				"      <StreetLines>"
				+ quo.getYng_User().getYng_Ubication().getStreet()
				+ "</StreetLines>\r\n" + 
				"      <PostalCode>"
				+ quo.getYng_User().getYng_Ubication().getPostalCode()
				+ "</PostalCode>\r\n" + 
				"      <CountryCode>"
				+ quo.getYng_User().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"     </Address>\r\n" + 
				"    </Recipient>\r\n" + 
				"    <ShippingChargesPayment>\r\n" + 
				"     <PaymentType>"
				+ "SENDER"
				+ "</PaymentType>\r\n" + 
				"    </ShippingChargesPayment>\r\n" + 
				"    <PackageCount>"
				+ "1"
				+ "</PackageCount>\r\n" + 
				"    <RequestedPackageLineItems>\r\n" + 
				"     <GroupPackageCount>"
				+ "1"
				+ "</GroupPackageCount>\r\n" + 
				"     <InsuredValue>\r\n" + 
				"      <Currency>"
				+ quo.getYng_Item().getYng_Ubication().getYng_Country().getCurrency()
				+ "</Currency>\r\n" + 
				"      <Amount>"
				+ quo.getYng_Item().getPrice()
				+ "</Amount>\r\n" + 
				"     </InsuredValue>\r\n" + 
				"     <Weight>\r\n" + 
				"      <Units>"
				+ "KG"
				+ "</Units>\r\n" + 
				"      <Value>"
				+ getProductByIdItem.getProductWeight()
				+ "</Value>\r\n" + 
				"     </Weight>\r\n" + 
				"     <Dimensions>\r\n" + 
				"      <Length>"
				+ getProductByIdItem.getProductLength()
				+ "</Length>\r\n" + 
				"      <Width>"
				+ getProductByIdItem.getProductWidth()
				+ "</Width>\r\n" + 
				"      <Height>"
				+ getProductByIdItem.getProductHeight()
				+ "</Height>\r\n" + 
				"      <Units>"
				+ "CM"
				+ "</Units>\r\n" + 
				"     </Dimensions>\r\n" + 
				"     <SpecialServicesRequested/>\r\n" + 
				"    </RequestedPackageLineItems>\r\n" + 
				"   </RequestedShipment>\r\n" + 
				"  </RateRequest>\r\n" + 
				" </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
	}

	public String FedexLocation(Yng_Quote quo) {

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") {
	        public StringBuffer format(Date date, StringBuffer toAppendTo, java.text.FieldPosition pos) {
	            StringBuffer toFix = super.format(date, toAppendTo, pos);
	            //return toFix.insert(toFix.length()-2, ':');
	            return toFix;
	        };
	    };
	    // Usage:
	    String fechaA=dateFormat.format(new Date());
	    System.out.println("fechaA:  "+fechaA);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
				" <soapenv:Body>\r\n" + 
				"  <SearchLocationsRequest xmlns=\"http://fedex.com/ws/locs/v7\">\r\n" + 
				"   <WebAuthenticationDetail>\r\n" + 
				"    <UserCredential>\r\n" + 
				"     <Key>"
				+ FedEXAuthenticationKey
				+ "</Key>\r\n" + 
				"     <Password>"
				+ FedexPassword
				+ "</Password>\r\n" + 
				"    </UserCredential>\r\n" + 
				"   </WebAuthenticationDetail>\r\n" + 
				"   <ClientDetail>\r\n" + 
				"    <AccountNumber>"
				+ FedExAccountNumber
				+ "</AccountNumber>\r\n" + 
				"    <MeterNumber>"
				+ FedExMeterNumber
				+ "</MeterNumber>\r\n" + 
				"   </ClientDetail>\r\n" + 
				"   <TransactionDetail>\r\n" + 
				"    <CustomerTransactionId>java sample - Locations Search</CustomerTransactionId>\r\n" + 
				"   </TransactionDetail>\r\n" + 
				"   <Version>\r\n" + 
				"    <ServiceId>locs</ServiceId>\r\n" + 
				"    <Major>7</Major>\r\n" + 
				"    <Intermediate>0</Intermediate>\r\n" + 
				"    <Minor>0</Minor>\r\n" + 
				"   </Version>\r\n" + 
				"   <EffectiveDate>"
				+ fechaA
				+ "</EffectiveDate>\r\n" + 
				"   <LocationsSearchCriterion>ADDRESS</LocationsSearchCriterion>\r\n" + 
				"   <Address>\r\n" + 
				"    <StreetLines>10 FedEx Pkwy</StreetLines>\r\n" + 
				"    <PostalCode>"
				+ quo.getYng_User().getYng_Ubication().getPostalCode()
				+ "</PostalCode>\r\n" + 
				"    <CountryCode>"
				+ quo.getYng_User().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"   </Address>\r\n" + 
				"   <PhoneNumber>"
				+ quo.getYng_User().getPhone()
				+ "</PhoneNumber>\r\n" + 
				"   <MultipleMatchesAction>RETURN_ALL</MultipleMatchesAction>\r\n" + 
				"   <SortDetail>\r\n" + 
				"    <Criterion>DISTANCE</Criterion>\r\n" + 
				"    <Order>LOWEST_TO_HIGHEST</Order>\r\n" + 
				"   </SortDetail>\r\n" + 
				"   <Constraints>\r\n" + 
				"    <RadiusDistance>\r\n" + 
				"     <Value>5</Value>\r\n" + 
				"     <Units>KM</Units>\r\n" + 
				"    </RadiusDistance>\r\n" + 
				"    <SupportedRedirectToHoldServices>FEDEX_GROUND</SupportedRedirectToHoldServices>\r\n" + 
				"    <RequiredLocationAttributes>ALREADY_OPEN</RequiredLocationAttributes>\r\n" + 
				"    <ResultsToSkip>1</ResultsToSkip>\r\n" + 
				"    <LocationContentOptions>HOLIDAYS</LocationContentOptions>\r\n" + 
				"    <LocationTypesToInclude>FEDEX_OFFICE</LocationTypesToInclude>\r\n" + 
				"   </Constraints>\r\n" + 
				"  </SearchLocationsRequest>\r\n" + 
				" </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
	}

	public String FedexShipping(Yng_Person per,Yng_Shipping shi,Yng_Person perItem,Yng_Product pro) {

    	FedexPassword= standard.getValue();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ") {
	        public StringBuffer format(Date date, StringBuffer toAppendTo, java.text.FieldPosition pos) {
	            StringBuffer toFix = super.format(date, toAppendTo, pos);
	            return toFix.insert(toFix.length()-2, ':');
	        };
	    };
	    String fechaA=dateFormat.format(new Date());
		return " <?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
				" <soapenv:Body>\r\n" + 
				"  <ProcessShipmentRequest xmlns=\"http://fedex.com/ws/ship/v21\">\r\n" + 
				"   <WebAuthenticationDetail>\r\n" + 
				"    <UserCredential>\r\n" + 
				"     <Key>"
				+ FedEXAuthenticationKey
				+ "</Key>\r\n" + 
				"     <Password>"
				+ FedexPassword
				+ "</Password>\r\n" + 
				"    </UserCredential>\r\n" + 
				"   </WebAuthenticationDetail>\r\n" + 
				"   <ClientDetail>\r\n" + 
				"    <AccountNumber>"
				+ FedExAccountNumber
				+ "</AccountNumber>\r\n" + 
				"    <MeterNumber>"
				+ FedExMeterNumber
				+ "</MeterNumber>\r\n" + 
				"   </ClientDetail>\r\n" + 
				"   <TransactionDetail>\r\n" + 
				"    <CustomerTransactionId>java sample - International Ground Shipment</CustomerTransactionId>\r\n" + 
				"   </TransactionDetail>\r\n" + 
				"   <Version>\r\n" + 
				"    <ServiceId>ship</ServiceId>\r\n" + 
				"    <Major>21</Major>\r\n" + 
				"    <Intermediate>0</Intermediate>\r\n" + 
				"    <Minor>0</Minor>\r\n" + 
				"   </Version>\r\n" + 
				"   <RequestedShipment>\r\n" + 
				"    <ShipTimestamp>"
				+ fechaA
				+ "</ShipTimestamp>\r\n" + 
				"    <DropoffType>REGULAR_PICKUP</DropoffType>\r\n" + 
				"    <ServiceType>FEDEX_GROUND</ServiceType>\r\n" + 
				"    <PackagingType>YOUR_PACKAGING</PackagingType>\r\n" + 
				"    <Shipper>\r\n" + 
				"     <Contact>\r\n" + 
				"      <PersonName>"
				+ perItem.getName()
				+ "</PersonName>\r\n" + 
				"      <CompanyName>"
				+ "Yingul"
				+ "</CompanyName>\r\n" + 
				"      <PhoneNumber>"
				+ perItem.getYng_User().getPhone()
				+ "</PhoneNumber>\r\n" + 
				"     </Contact>\r\n" + 
				"     <Address>\r\n" + 
				"      <StreetLines>"
				+ perItem.getYng_User().getYng_Ubication().getStreet()
				+ "</StreetLines>\r\n" + 
				"      <City>"
				+ perItem.getYng_User().getYng_Ubication().getYng_City().getName()
				+ "</City>\r\n" + 
				"      <StateOrProvinceCode>"
				+ ""
				+ "</StateOrProvinceCode>\r\n" + 
				"      <PostalCode>"
				+ perItem.getYng_User().getYng_Ubication().getPostalCode()
				+ "</PostalCode>\r\n" + 
				"      <CountryCode>"
				+ perItem.getYng_User().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"     </Address>\r\n" + 
				"    </Shipper>\r\n" + 
				"    <Recipient>\r\n" + 
				"     <Contact>\r\n" + 
				"      <CompanyName>"
				+ "Yingul"//preguntar
				+ "</CompanyName>\r\n" + 
				"      <PhoneNumber>"
				+per.getYng_User().getPhone()
				+ "</PhoneNumber>\r\n" + 
				"     </Contact>\r\n" + 
				"     <Address>\r\n" + 
				"      <StreetLines>"
				+ per.getYng_User().getYng_Ubication().getStreet()
				+ "</StreetLines>\r\n" + 
				"      <City>"
				+ per.getYng_User().getYng_Ubication().getYng_City().getName()
				+ "</City>\r\n" + 
				"      <StateOrProvinceCode>"
				+ ""
				+ "</StateOrProvinceCode>\r\n" + 
				"      <PostalCode>"
				+ per.getYng_User().getYng_Ubication().getPostalCode()
				+ "</PostalCode>\r\n" + 
				"      <CountryCode>"
				+ per.getYng_User().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"      <Residential>"
				+ "false"
				+ "</Residential>\r\n" + 
				"     </Address>\r\n" + 
				"    </Recipient>\r\n" + 
				"    <ShippingChargesPayment>\r\n" + 
				"     <PaymentType>SENDER</PaymentType>\r\n" + 
				"     <Payor>\r\n" + 
				"      <ResponsibleParty>\r\n" + 
				"       <AccountNumber>"
				+ FedExAccountNumber
				+ "</AccountNumber>\r\n" + 
				"       <Contact/>\r\n" + 
				"       <Address>\r\n" + 
				"        <CountryCode>"
				+ pro.getYng_Item().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"       </Address>\r\n" + 
				"      </ResponsibleParty>\r\n" + 
				"     </Payor>\r\n" + 
				"    </ShippingChargesPayment>\r\n" + 
				"    <CustomsClearanceDetail>\r\n" + 
				"     <DutiesPayment>\r\n" + 
				"      <PaymentType>SENDER</PaymentType>\r\n" + 
				"      <Payor>\r\n" + 
				"       <ResponsibleParty>\r\n" + 
				"        <AccountNumber>"
				+ FedExAccountNumber//preguntar
				+ "</AccountNumber>\r\n" + 
				"        <Contact/>\r\n" + 
				"        <Address>\r\n" + 
				"         <CountryCode>"
				+ pro.getYng_Item().getYng_Ubication().getYng_Country().getCountryCod()
				+ "</CountryCode>\r\n" + 
				"        </Address>\r\n" + 
				"       </ResponsibleParty>\r\n" + 
				"      </Payor>\r\n" + 
				"     </DutiesPayment>\r\n" + 
				"     <DocumentContent>NON_DOCUMENTS</DocumentContent>\r\n" + 
				"     <CustomsValue>\r\n" + 
				"      <Currency>"
				+  perItem.getYng_User().getYng_Ubication().getYng_Country().getCurrency()
				+ "</Currency>\r\n" + 
				"      <Amount>"
				+ pro.getYng_Item().getPrice()
				+ "</Amount>\r\n" + 
				"     </CustomsValue>\r\n" + 
				"     <Commodities>\r\n" + 
				"      <NumberOfPieces>"
				+ "1"
				+ "</NumberOfPieces>\r\n" + 
				"      <Description>"
				+ pro.getYng_Item().getDescription()
				+ "</Description>\r\n" + 
				"      <CountryOfManufacture>"
				+ ""
				+ "</CountryOfManufacture>\r\n" + 
				"      <HarmonizedCode>"
				+ "490199009100"
				+ "</HarmonizedCode>\r\n" + 
				"      <Weight>\r\n" + 
				"       <Units>"
				+ "KG"
				+ "</Units>\r\n" + 
				"       <Value>"
				+ pro.getProductWeight()
				+ "</Value>\r\n" + 
				"      </Weight>\r\n" + 
				"      <Quantity>"
				+ "1.0"
				+ "</Quantity>\r\n" + 
				"      <QuantityUnits>"
				+ "EA"
				+ "</QuantityUnits>\r\n" + 
				"      <UnitPrice>\r\n" + 
				"       <Currency>"
				+ perItem.getYng_User().getYng_Ubication().getYng_Country().getCurrency()
				+ "</Currency>\r\n" + 
				"       <Amount>"
				+ pro.getYng_Item().getPrice()
				+ "</Amount>\r\n" + 
				"      </UnitPrice>\r\n" + 
				"      <CustomsValue>\r\n" + 
				"       <Currency>"
				+ pro.getYng_Item().getYng_Ubication().getYng_Country().getCurrency()
				+ "</Currency>\r\n" + 
				"       <Amount>"
				+ pro.getYng_Item().getPrice()
				+ "</Amount>\r\n" + 
				"      </CustomsValue>\r\n" + 
				"     </Commodities>\r\n" + 
				"    </CustomsClearanceDetail>\r\n" + 
				"    <LabelSpecification>\r\n" + 
				"     <LabelFormatType>COMMON2D</LabelFormatType>\r\n" + 
				"     <ImageType>PDF</ImageType>\r\n" + 
				"    </LabelSpecification>\r\n" + 
				"    <PackageCount>1</PackageCount>\r\n" + 
				"    <RequestedPackageLineItems>\r\n" + 
				"     <SequenceNumber>"
				+ "1"
				+ "</SequenceNumber>\r\n" + 
				"     <GroupPackageCount>1</GroupPackageCount>\r\n" + 
				"     <Weight>\r\n" + 
				"      <Units>"
				+ "KG"
				+ "</Units>\r\n" + 
				"      <Value>"
				+ pro.getProductWidth()
				+ "</Value>\r\n" + 
				"     </Weight>\r\n" + 
				"     <Dimensions>\r\n" + 
				"      <Length>"
				+ pro.getProductLength()
				+ "</Length>\r\n" + 
				"      <Width>"
				+ pro.getProductWidth()
				+ "</Width>\r\n" + 
				"      <Height>"
				+ pro.getProductHeight()
				+ "</Height>\r\n" + 
				"      <Units>"
				+ "CM"
				+ "</Units>\r\n" + 
				"     </Dimensions>\r\n" + 
				"     <CustomerReferences>\r\n" + 
				"      <CustomerReferenceType>CUSTOMER_REFERENCE</CustomerReferenceType>\r\n" + 
				"      <Value>CR1234</Value>\r\n" + 
				"     </CustomerReferences>\r\n" + 
				"     <CustomerReferences>\r\n" + 
				"      <CustomerReferenceType>INVOICE_NUMBER</CustomerReferenceType>\r\n" + 
				"      <Value>IV1234</Value>\r\n" + 
				"     </CustomerReferences>\r\n" + 
				"     <CustomerReferences>\r\n" + 
				"      <CustomerReferenceType>P_O_NUMBER</CustomerReferenceType>\r\n" + 
				"      <Value>PO1234</Value>\r\n" + 
				"     </CustomerReferences>\r\n" + 
				"     <SpecialServicesRequested>\r\n" + 
				"      <SpecialServiceTypes>COD</SpecialServiceTypes>\r\n" + 
				"      <CodDetail>\r\n" + 
				"       <CodCollectionAmount>\r\n" + 
				"        <Currency>"
				+ pro.getYng_Item().getYng_Ubication().getYng_Country().getCurrency()
				+ "</Currency>\r\n" + 
				"        <Amount>"
				+ pro.getYng_Item().getPrice()
				+ "</Amount>\r\n" + 
				"       </CodCollectionAmount>\r\n" + 
				"       <CollectionType>ANY</CollectionType>\r\n" + 
				"      </CodDetail>\r\n" + 
				"     </SpecialServicesRequested>\r\n" + 
				"    </RequestedPackageLineItems>\r\n" + 
				"   </RequestedShipment>\r\n" + 
				"  </ProcessShipmentRequest>\r\n" + 
				" </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
	}

	public void inirCre(String FedEXAuthenticationKeyI,String FedExMeterNumberI, String FedExAccountNumberI,String FedexPasswordI) {
		   FedEXAuthenticationKey= FedEXAuthenticationKeyI;
		   FedExMeterNumber=FedExMeterNumberI;
		   FedExAccountNumber=FedExAccountNumberI;
		   FedexPassword=FedexPasswordI;
	}
}
