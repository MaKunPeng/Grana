package com.dust.xml.parse;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.dust.xml.data.DustConfig;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
public class SAXParserTool {

    public Object parseXmlString(String xml, Class clazz) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(xml, new MyHandler());
        return null;
    }
    
    /**
     * 
     * 
     * @author Aaron
     *
     */
    class MyHandler extends DefaultHandler {
        private DustConfig config;
        private Class clazz;
        private List<String> hosts;
        private String tag;
        
        @Override
        public void startDocument() throws SAXException {
            this.config = new DustConfig();
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            if (qName.equals("hosts")) {
//                hosts.add(e)
            }
            tag = qName;
        }
        
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            
            super.endElement(uri, localName, qName);
        }
        
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            
            super.characters(ch, start, length);
        }
    }
}
