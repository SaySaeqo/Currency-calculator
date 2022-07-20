package net.atos.recruitment.currcalc;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class Calculator {

    private final HashMap<String, Double> currenciesRates = new HashMap<>();

    public Calculator(String file_name){
        try{
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File(file_name));
            NodeList cubes = doc.getDocumentElement().getElementsByTagName("Cube");

            int i = 2; // ignoring cube with timestamp and the top cube
            for (Node cube = cubes.item(i); cube != null; cube = cubes.item(++i)){
                String currency = cube.getAttributes().getNamedItem("currency").getTextContent();
                Double rate = Double.parseDouble(cube.getAttributes().getNamedItem("rate").getTextContent());
                currenciesRates.put(currency, rate);
            }

        } catch(Exception ex){
            ex.printStackTrace(System.err);
        }
    }

    public Set<String> getAllSupportedCurrencies(){
        return currenciesRates.keySet();
    }

    public double calc(String from, String to, double value){
        return Math.round(value * currenciesRates.get(to) / currenciesRates.get(from) * 100) / 100.0 ;
    }
}
