/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphoneclient;

import com.server.smartphonedata.entities.Company;
import com.server.smartphonedata.entities.Salesdata;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author gail
 */
public class SmartPhoneClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CompanyJerseyClient client = new CompanyJerseyClient();
        Company company = client.find_XML(Company.class, "2");
        System.out.println("Company = " + company.getCompanyname());

        // Change Nokia company name to Nokia Aktiebolag
        Response response = client.find_XML(Response.class, "1");
        company = response.readEntity(Company.class);
        System.out.println("Change Nokia company name to Nokia Aktiebolag");
        System.out.println("Was: Company = " + company.companynameProperty().get());
        company.setCompanyname("Nokia Aktiebolag");
        client.edit_XML(company, "1");
        company = client.find_XML(Company.class, "1");
        System.out.println("Now: Company = " + company.getCompanyname());
        company.setCompanyname("Nokia");
        client.edit_XML(company, "1");
        company = client.find_XML(Company.class, "1");
        System.out.println("Back to : Company = " + company.getCompanyname());
        System.out.println(company.getSalesdataCollection());

        company = client.find_JSON(Company.class, "2");
        System.out.println("Company = " + company.getCompanyname());

        GenericType<List<Company>> gType1 = new GenericType<List<Company>>() {
        };
        response = client.findAll_XML(Response.class);
        List<Company> companies = response.readEntity(gType1);
        System.out.println("Companies = " + companies);

        SalesdataJerseyClient client2 = new SalesdataJerseyClient();
        Salesdata data = client2.find_XML(Salesdata.class, "1");
        System.out.println("Salesdata = " + data.getSalesyear() + ", "
                + data.getCompanyid() + ", "
                + data.getUnitsinmillions());

        GenericType<List<Salesdata>> gType2 = new GenericType<List<Salesdata>>() {
        };
        response = client2.findAll_XML(Response.class);
        List<Salesdata> dataList = response.readEntity(gType2);
        System.out.println("Data List = " + dataList);

        // print out all the data
        for (Company c : companies) {
            System.out.println("Data for Company: " + c.companynameProperty().get());
            for (Salesdata dataItem : dataList) {
                if (dataItem.getCompanyid().getCompanyid().equals(c.getCompanyid())) {
                    System.out.println("Year=" + dataItem.getSalesyear() + ", " + "Units Sold in Millions = " + dataItem.getUnitsinmillions());
                }
            }

        }

        // test JSON
        System.out.println("Testing JSON");
        response = client2.findAll_JSON(Response.class);
        List<Salesdata> dataList2 = response.readEntity(gType2);
        System.out.println("Data List = " + dataList2);

        // print out all the data
        for (Company c : companies) {
            System.out.println("Data for Company: " + c.companynameProperty().get());
            for (Salesdata dataItem : dataList2) {
                if (dataItem.getCompanyid().getCompanyid().equals(c.getCompanyid())) {
                    System.out.println("Year=" + dataItem.getSalesyear() + ", " + "Units Sold in Millions = " + dataItem.getUnitsinmillions());
                }
            }

        }
    }

}
