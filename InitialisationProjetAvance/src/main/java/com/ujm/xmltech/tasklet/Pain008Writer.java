package com.ujm.xmltech.tasklet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.ujm.xmltech.utils.BankSimulationConstants;

public class Pain008Writer implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution step, ChunkContext context) throws Exception {
		//L'object va maintenant etre de scruter en bdd (potentiellement dans une etape precedente) afin de determiner si le fichier a ete totalement traite
		//Si c'est le cas il faut recuperer toutes les informations dont vous avez besoin et boucler dessus via la methode write pour les ecrire dans un fichier
		return RepeatStatus.FINISHED;
	}

	public void write(Object item) {
		//Added a random in order to have a different file each time
		File file = new File(BankSimulationConstants.OUT_DIRECTORY + "report" + Math.random() + ".xml");
		OutputStream out;
		try {
			out = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(out);
			JAXBContext ctx = JAXBContext.newInstance(item.getClass());
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
			//writer file header
			String documentBase = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.008.001.02\">\n";
			writer.write(documentBase);
			//write header item
			writer.write(getXMLFragment(item, "CstmrDrctDbtInitn", marshaller) + "\n");
			//write footer
			String documentEnd = "\n</Document>";
			writer.write(documentEnd);
			writer.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transform an object into xml string
	 * 
	 * @param object
	 * @param name
	 * @param marshaller
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getXMLFragment(Object object, String name, Marshaller marshaller) {
		StringWriter writer = new StringWriter();
		try {
			marshaller.marshal(new JAXBElement(new QName("", name, ""), object.getClass(), object), writer);
		} catch (JAXBException e) {
			return null;
		}
		String originFragment = writer.toString();
		String fragment = originFragment.replaceAll("<" + name + ".*>", "<" + name + ">").replaceAll("<ns2:", "<").replaceAll("</ns2:", "</");
		fragment = fragment.replaceAll("&quot;", "\"").replaceAll("&apos;", "\'");

		return fragment;
	}

}
