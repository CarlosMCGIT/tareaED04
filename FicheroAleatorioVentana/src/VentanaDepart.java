
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.swing.*;

/**
 * 
 * Clase que modela la gestión de departamentos
 * Esta clase modela una interfaz gráfica con botones que es capaz de  gestionar diferentes
 * departamentos, con opciones de inserción, consulta, borrar, modificar, visualizar por consola
 * limpiar los campos y cerrar el programa.
 * 
 * 
 * @author Carlos Mateo Colilla
 * 
 * @version 1.0
 *
 */

public class VentanaDepart extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JTextField num = new JTextField(10);
	JTextField nombre = new JTextField(25);
	JTextField loc = new JTextField(25);

	JLabel mensaje = new JLabel(" ----------------------------- ");
	JLabel titulo = new JLabel("GESTI�N DE DEPARTAMENTOS.");

	JLabel lnum = new JLabel("NUMERO DEPARTAMENTO:");
	JLabel lnom = new JLabel("NOMBRE:");
	JLabel lloc = new JLabel("LOCALIDAD:");

	JButton balta = new JButton("Insertar Depar.t");
	JButton consu = new JButton("Consultar Depart.");
	JButton borra = new JButton("Borrar Depart.");
	JButton breset = new JButton("Limpiar datos.");
	JButton modif = new JButton("Modificar Departamento.");
	JButton ver = new JButton("Ver por consola.");
	JButton fin = new JButton("CERRAR");
	Color c; // para poner colores
	// WHITE,LIGHTGRAY,GRAY,DARKGRAY,BLUE,BLACK,RED,MAGENTA,PINK,ORANGE,CYAN,GREEN,YELLOW
	
	/**
	 * Cadena de texto que muestra que no existe departamento 
	 */
	
	private static String existedepart;
	
	/**
	 * Cadena de texto que lanza un error de departamento
	 */
	
	private static String depar_error;

	/**
	 * Constructor que inicializa una instancia de VentanaDepart con un JFrame pasado
	 * por parámetro
	 * @param f
	 */
	public VentanaDepart(JFrame f) {
		setTitle("GESTI�N DE DEPARTAMENTOS.");

		JPanel p0 = new JPanel();
		c = Color.CYAN;
		p0.add(titulo);
		p0.setBackground(c);

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(lnum);
		p1.add(num);
		p1.add(consu);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(lnom);
		p2.add(nombre);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout());
		p3.add(lloc);
		p3.add(loc);

		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		c = Color.YELLOW;
		p4.add(balta);
		p4.add(borra);
		p4.add(modif);
		p4.setBackground(c);

		JPanel p5 = new JPanel();
		p4.setLayout(new FlowLayout());
		c = Color.PINK;
		p5.add(breset);
		p5.add(ver);
		p5.add(fin);
		p5.setBackground(c);

		JPanel p7 = new JPanel();
		p7.setLayout(new FlowLayout());
		p7.add(mensaje);

		// para ver la ventana y colocar los controles verticalmente
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		// a�adir los panel al frame
		add(p0);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p7);
		pack(); // hace que se coloquen alineados los elementos de cada JPanel

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		balta.addActionListener(this);
		breset.addActionListener(this);
		fin.addActionListener(this);
		consu.addActionListener(this);
		borra.addActionListener(this);
		modif.addActionListener(this);
		ver.addActionListener(this);
	}

	
	/**
	 * Método que genera una acción en función del botón que se le pulse,
	 * como opciones tenemos dar de alta, consultar, borrar, modificar, y salir
	 * @throws Error si no se ha podilo leer el archivo AleatorioDep.dat
	 * @param e evento
	 */
	public void actionPerformed(ActionEvent e) {
		int dep, confirm;
		existedepart = "DEPARTAMENTO EXISTE.";
		if (e.getSource() == balta) { // SE PULSA EL BOTON alta
			altadepart();
		}

		/**
		 * Cadena de texto que muestra que no existe un departamento
		 */
		final String NOEXISTEDEPART = "DEPARTAMENTO NO EXISTE.";
		depar_error = "DEPARTAMENTO ERR�NEO";
		if (e.getSource() == consu) { // SE PULSA EL BOTON consultar
			consuldepart(NOEXISTEDEPART);

		}

		if (e.getSource() == borra) { // SE PULSA EL BOTON borrar
			borradepart(NOEXISTEDEPART);
		}
		if (e.getSource() == modif) { // SE PULSA EL BOTON modificar
			modifdepart(NOEXISTEDEPART);
		}
		if (e.getSource() == fin) { // SE PULSA EL BOTON salir
			System.exit(0);
			// dispose();
		}
		if (e.getSource() == ver) { // SE PULSA EL BOTON ver por consola
			try {
				mensaje.setText("Visualizando el fichero por la consolaa.....");
				verporconsola();
			} catch (IOException e1) {
				System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");
				// e1.printStackTrace();
			}
		}
		if (e.getSource() == breset) { // SE PULSA EL BOTON limpiar
			mensaje.setText(" has pulsado el boton limpiar..");
			num.setText(" ");
			nombre.setText(" ");
			loc.setText(" ");
		}
	}

	
	/**
	 * Método que modifica el departamento
	 * @param NOEXISTEDEPART Cadena de texto "No existe departamento"
	 * @throws IOException ex2
	 */
	private void modifdepart(final String NOEXISTEDEPART) {
		int dep;
		int confirm;
		mensaje.setText(" has pulsado el boton Modificar.");
		try {
			dep = Integer.parseInt(num.getText());
			if (dep > 0)
				if (consultar(dep)) {
					mensaje.setText(existedepart);
					confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE MODIFICAR...", "AVISO MODIFICACI�N.",
							JOptionPane.OK_CANCEL_OPTION);
					// si devuelve 0 es OK
					// mensaje.setText(" has pulsado el boton Borrar "+ confirm);
					if (confirm == 0) {
						modificar(dep);
						mensaje.setText(" REGISTRO MODIFICADO: " + dep);
					}
				} else {
					mensaje.setText(NOEXISTEDEPART);
					nombre.setText(" ");
					loc.setText(" ");
				}
			else
				mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

		} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
		{
			mensaje.setText(depar_error);
		} catch (IOException ex2) {
			mensaje.setText(" ERRORRR EN EL FICHERO. Fichero no existe. (MODIFICAR)");
		}
	}

	/**
	 * Método que borra el departamento
	 * @param NOEXISTEDEPART Cadena de texto "No existe departamento"
	 * @throws IOException ex2
	 */
	private void borradepart(final String NOEXISTEDEPART) {
		int dep;
		int confirm;
		mensaje.setText(" has pulsado el boton Borrar");
		try {
			dep = Integer.parseInt(num.getText());
			if (dep > 0)
				if (consultar(dep)) {
					mensaje.setText(existedepart);
					visualiza(dep);
					confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE BORRAR...", "AVISO BORRADO.",
							JOptionPane.OK_CANCEL_OPTION);
					// si devuelve 0 es OK
					// mensaje.setText(" has pulsado el boton Borrar "+ confirm);
					if (confirm == 0) {
						borrar(dep);
						mensaje.setText(" REGISTRO BORRADOO: " + dep);
						nombre.setText(" ");
						loc.setText(" ");
					}
				} else {
					mensaje.setText(NOEXISTEDEPART);
					nombre.setText(" ");
					loc.setText(" ");
				}
			else
				mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

		} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
		{
			mensaje.setText(depar_error);
		} catch (IOException ex2) {
			mensaje.setText("ERRORRR EN EL FICHERO. Fichero no existe. (BORRAR)");
		}
	}

	/**
	 * Método que consulta el departamento
	 * @param NOEXISTEDEPART Cadena de texto "No existe departamento"
	 * @throws IOException ex2
	 */
	private void consuldepart(final String NOEXISTEDEPART) {
		int dep;
		mensaje.setText(" has pulsado el boton alta");
		try {
			dep = Integer.parseInt(num.getText());
			if (dep > 0)
				if (consultar(dep)) {
					mensaje.setText(existedepart);
					visualiza(dep);
				} else {
					mensaje.setText(NOEXISTEDEPART);
					nombre.setText(" ");
					loc.setText(" ");
				}
			else
				mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

		} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
		{
			mensaje.setText(depar_error);
		} catch (IOException ex2) {
			mensaje.setText(" ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
		}
	}

	/**
	 * Método que da de alta el departamento
	 * @throws IOException ex2
	 */
	private void altadepart() {
		int dep;
		mensaje.setText(" has pulsado el boton alta");
		try {
			dep = Integer.parseInt(num.getText());
			if (dep > 0)
				if (consultar(dep))
					mensaje.setText(existedepart);
				else {
					mensaje.setText("NUEVO DEPARTAMENTO.");
					grabar(dep, nombre.getText(), loc.getText());
					mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
				}
			else
				mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

		} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
		{
			mensaje.setText("DEPARTAMENTO ERR�NEO.");
		} catch (IOException ex2) {
			mensaje.setText("ERRORRR EN EL FICHERO. Fichero no existe. (ALTA)");
			// lo creo

		}
	}
	/**
	 * Método que muestra por consola el departamento
	 * @throws IOException
	 */
	public void verporconsola() throws IOException {
		String nom = "", loc = "";
		int dep = 0;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		char cad[] = new char[10], aux;
		if (file.length() > 0) {
			pos = 0; // para situarnos al principio
			System.out.println(" ------------------------------------------");
			System.out.println(" - - - VISUALIZO POR CONSOLAAAAA ");
			for (;;) { // recorro el fichero, visualiza tambi�n las posiciones vac�as
				file.seek(pos);
				dep = file.readInt(); // obtengo el dep
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();// recorro uno a uno los caracteres del apellido
					cad[i] = aux; // los voy guardando en el array
				}
				nom = new String(cad);// convierto a String el array
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();
					cad[i] = aux;
				}
				loc = new String(cad);// convierto a String el array
				System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loc);
				pos = pos + 44;
				// Si he recorrido todos los bytes salgo del for
				if (file.getFilePointer() == file.length())
					break;

			} // fin bucle for
			file.close(); // cerrar fichero
			System.out.println(" ------------------------------------------");
		} else // esto s�lo sale la primera vez
			System.out.println(" ---------FICHERO VACI�IOOOO --------------------");
	}// fin verporconsola

	
	
	/**
	 * Método para consultar el departamento
	 * @param dep Número entero con el valor numérico del departamento
	 * @return Booleano verdadero si se ha leído el departamento
	 * Y falso si el departamento está vacío o no ha podido ser leído
	 * @throws IOException
	 */
	boolean consultar(int dep) throws IOException {
		long pos;
		int depa;
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		// Calculo del reg a leer
		try {
			pos = 44 * (dep - 1);
			if (file.length() == 0)
				return false; // si est� vac�o
			file.seek(pos);
			depa = file.readInt();
			file.close();
			System.out.println("Depart leido:" + depa);
			if (depa > 0)
				return true;
			else
				return false;
		} catch (IOException ex2) {
			System.out.println(" ERRORRR al leerrrrr..");
			return false;
		}
	} // fin consultar

	/**
	 * Método para visualizar por consola el departamento
	 * @param dep Número entero con el valor numérico del departamento
	 */
	void visualiza(int dep) {
		String nom = "", loca = "";
		long pos;
		int depa;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			depa = file.readInt();
			System.out.println("Depart leido:" + depa);
			char nom1[] = new char[10], aux, loc1[] = new char[10];
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				nom1[i] = aux;
			}
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				loc1[i] = aux;
			}
			nom = new String(nom1);
			loca = new String(loc1);
			System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loca);
			nombre.setText(nom);
			loc.setText(loca);
			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin visualiza

	
	/**
	 * Método para eliminar un departamento
	 * @param dep Número entero con el valor numérico del departamento
	 */
	void borrar(int dep) { // con borrar ponemos a 0 el dep que se quiere borrar
							// y a blancos el nombre y la localidad
		String nom = "", loca = "";
		StringBuffer buffer = null;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			int depp = 0;
			file.writeInt(depp);
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());

			buffer = new StringBuffer(loca);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			System.out.println("----REGISTRO BORRADO--------");

			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL BORRARRR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin borrar

	
	/**
	 * Método para modificar el departamento
	 * @param dep Número entero con el valor numérico del departamento
	 */
	void modificar(int dep) { // con modificar asignamos los datos tecleados
		String nom = "", loca = "";
		StringBuffer buffer = null;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			file.writeInt(dep);
			nom = nombre.getText();
			loca = loc.getText();
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			buffer = new StringBuffer(loca);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			System.out.println("----REGISTRO MODIFICADOOO--------");

			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL MODIFICARRR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin modificar

	/**
	 * Método para grabar un departamento en memoria
	 * @param dep Número entero con el valor numérico del departamento
	 * @param nom String con el nombre del departamento
	 * @param loc String con la localización del departamento
	 */
	void grabar(int dep, String nom, String loc) {
		long pos;
		StringBuffer buffer = null;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			// if (file.length()==0) return false; // si est� vac�o

			file.seek(pos);
			file.writeInt(dep);
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());// insertar nombre
			buffer = new StringBuffer(loc);
			buffer.setLength(10);
			file.writeChars(buffer.toString());// insertar loc
			file.close();
			System.out.println(" GRABADOOO el " + dep);
		} catch (IOException e1) {
			System.out.println("ERRROR AL grabarr AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin grabar
}// fin clase

// Comentario nuevo para hacer un segundo commit con git
