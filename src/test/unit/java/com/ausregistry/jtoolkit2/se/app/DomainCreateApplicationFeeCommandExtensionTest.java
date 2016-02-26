package com.ausregistry.jtoolkit2.se.app;

import com.ausregistry.jtoolkit2.Timer;
import com.ausregistry.jtoolkit2.se.CLTRID;
import com.ausregistry.jtoolkit2.se.Command;
import com.ausregistry.jtoolkit2.se.DomainCreateCommand;
import com.ausregistry.jtoolkit2.se.fee.DomainCreateFeeCommandExtension;
import com.ausregistry.jtoolkit2.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class DomainCreateApplicationFeeCommandExtensionTest {

    private static final XMLParser PARSER = new XMLParser();

    @Before
    public void setUp() throws Exception {
        Timer.setTime("20070101.010101");
        CLTRID.setClID("JTKUTEST");
    }

    @Test
    public void shouldCreateValidXmlWhenSupplyFeeExtension() throws SAXException {

        final Command cmd = new DomainCreateCommand("jtkutest.com.au", "jtkUT3st");
        final DomainCreateApplicationFeeCommandExtension ext =
                new DomainCreateApplicationFeeCommandExtension(BigDecimal.valueOf(10.00), "USD");

        try {
            cmd.appendExtension(ext);

            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<epp xmlns=\"urn:ietf:params:xml:ns:epp-1.0\""
                    + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                    + " xsi:schemaLocation=\"urn:ietf:params:xml:ns:epp-1.0 epp-1.0.xsd\">"
                    + "<command><create><create xmlns=\"urn:ietf:params:xml:ns:domain-1.0\" "
                    + "xsi:schemaLocation=\"urn:ietf:params:xml:ns:domain-1.0 domain-1.0.xsd\">"
                    + "<name>jtkutest.com.au</name><authInfo><pw>jtkUT3st</pw></authInfo></create></create>"
                    + "<extension>"
                    + "<create xmlns=\"urn:ietf:params:xml:ns:fee-0.6\">"
                    + "<currency>USD</currency>"
                    + "<fee>10.00</fee>"
                    + "</create>"
                    + "</extension>"
                    + "<clTRID>JTKUTEST.20070101.010101.0</clTRID></command></epp>", cmd.toXML());

        } catch (SAXException saxe) {
            fail(saxe.getMessage());
        }

    }

}

