import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.io.File;


public class CrearXMLconAle {

	public static void main(String args[]) throws IOException {

		
 // CÓDIGO PARA CREAR EL FICHERO ALEATORIO {
		
		File fichero = new File("C:\\Users\\Antonio\\Desktop\\UltimaAleatorios\\AntonioLopez\\AleatorioEmple.dat");

		// declara el fichero de acceso aleatorio
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");

		// arrays con los datos
		String apellido[] = { "FERNANDEZ", "GIL", "LOPEZ", "RAMOS", "SEVILLA", "CASILLA", "REY" };// apellidos

		int dep[] = { 10, 20, 10, 10, 30, 30, 20 }; // departamentos

		Double salario[] = { 1000.45, 2400.60, 3000.0, 1500.56, 2200.0, 1435.87, 2000.0 };// salarios

		StringBuffer buffer = null;// buffer para almacenar apellido

		int n = apellido.length;// numero de elementos del array

		for (int i = 0; i < n; i++) { // recorro los arrays

			file.writeInt(i + 1); // uso i+1 para identificar empleado
			buffer = new StringBuffer(apellido[i]);
			buffer.setLength(10); // 10 caracteres para el apellido
			file.writeChars(buffer.toString());// insertar apellido
			file.writeInt(dep[i]); // insertar departamento
			file.writeDouble(salario[i]);// insertar salario

		}

		file.close(); // cerrar fichero
		
		System.out.println("Fichero Aleatorio creado correctamente.");
    		System.out.println("HOLA MUNDO");
		
 // } SE CIERRA EL CÓDIGO PARA CREAR EL FICHERO ALEATORIO
		
		//================================================================================================================================
		
 // CÓDIGO PARA CREAR EL FICHERO XML A PARTIR DEL FICHERO ALEATORIO CREADO ARRIBA {

		File ficheroXML = new File("C:\\Users\\Antonio\\Desktop\\UltimaAleatorios\\AntonioLopez\\AleatorioEmple.dat");
		RandomAccessFile fileXML = new RandomAccessFile(ficheroXML, "r");

		int id, depXML, posicion = 0; // para situarnos al principio del fichero
		Double salarioXML;
		char apellidoXML[] = new char[10], aux;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "Empleados", null);
			document.setXmlVersion("1.0");

			for (;;) {

				fileXML.seek(posicion); // nos posicionamos
				id = fileXML.readInt(); // obtengo id de empleado

				for (int i = 0; i < apellidoXML.length; i++) {
					aux = fileXML.readChar();
					apellidoXML[i] = aux;
				}

				String apellidosXML = new String(apellidoXML);
				depXML = fileXML.readInt();
				salarioXML = fileXML.readDouble();

				if (id > 0) { // id validos a partir de 1

					Element raiz = document.createElement("empleado"); // nodo empleado
					document.getDocumentElement().appendChild(raiz);

					// añadir ID
					CrearElemento("id", Integer.toString(id), raiz, document);
					// Apellido
					CrearElemento("apellido", apellidosXML.trim(), raiz, document);
					// añadir DEP
					CrearElemento("dep", Integer.toString(depXML), raiz, document);
					// añadir salario
					CrearElemento("salario", Double.toString(salarioXML), raiz, document);
				}

				posicion = posicion + 36; // me posiciono para el sig empleado
				if (fileXML.getFilePointer() == fileXML.length())
					break;

			} // fin del for que recorre el fichero

			Source source = new DOMSource(document);
			Result result = new StreamResult(
					new java.io.File("C:\\Users\\Antonio\\Desktop\\UltimaAleatorios\\AntonioLopez\\Empleados.xml"));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			System.err.println("Error: " + e);
		}

		fileXML.close(); // cerrar fichero
		
		System.out.println("Fichero XML creado correctamente.");

	}// fin de main

	// Inserción de los datos del empleado
	static void CrearElemento(String datoEmple, String valor, Element raiz, Document document) {
		Element elem = document.createElement(datoEmple);
		Text text = document.createTextNode(valor); // damos valor
		raiz.appendChild(elem); // pegamos el elemento hijo a la raiz
		elem.appendChild(text); // pegamos el valor
		
		
		
// } SE CIERRA EL CÓDIGO PARA CREAR EL FICHERO XML
		
		
	}
	

}

