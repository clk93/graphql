package xml2object;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
        List<String> split = Arrays.asList(value.split("\n"));
        Map<String, String> objectObjectHashMap = new HashMap<>();
        split.forEach(s -> {
            String[] split1 = s.split("=");
            if(split1.length<= 1){
                objectObjectHashMap.put(split1[0], null);
            }
            else{
                objectObjectHashMap.put(split1[0], split1[1]);
            }
        });

        String standardXML = objectObjectHashMap.get("standardXML");
        System.out.println(standardXML);
    }

    private static String getAttributeByKey(List<Attribute> attribute, String key) {
        Attribute result = attribute.stream().filter(a -> key.equals(a.getKey())).findFirst().orElse(null);
        if(Objects.nonNull(result)){
            return result.getValue();
        }
        return null;
    }


}
