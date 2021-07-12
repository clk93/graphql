package xml2object;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class XMLMapperExamleConf {

    public static void main(String[] args) {

        XmlMapper xmlMapper = new XmlMapper();
        URL url = Resources.getResource("example.xml");
        String s= "";
        try {
            s = Resources.toString(url, Charsets.UTF_8);
            Order exportCadStrukturVarianteXData = xmlMapper.readValue(s, Order.class);
            doSuperXMap(exportCadStrukturVarianteXData);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void doSuperXMap(Order source) {
        SuperX result = new SuperX();
        result.setUser(source.getUser());
        result.setTimestamp(source.getTimestamp());
        result.setStartdate(source.getStartdate());
        result.setRunas(source.getRunas());
        result.setObjectid(source.getObjectid());
        result.setExportIds(getAttributeByKey(source.getAttribute(), "exportIds"));
        result.setIntegRunAsContextRole(getAttributeByKey(source.getAttribute(), "integRunAsContextRole"));


        String value = source.getAttribute().get(1).getValue();
        Map<String, String> collect1 = Arrays.stream(value.split("\\n"))
                .map(entry -> entry.split("="))
                .filter(entry -> entry.length ==2)
                .collect(Collectors.toMap(entry -> entry[0].trim(), entry -> entry[1].trim()));
        System.out.println(collect1);
        System.out.println(collect1.get("variante.label"));

    }

    private static String getAttributeByKey(List<Attribute> attribute, String key) {
        Attribute result = attribute.stream().filter(a -> key.equals(a.getKey())).findFirst().orElse(null);
        if(Objects.nonNull(result)){
            return result.getValue();
        }
        return null;
    }


}
