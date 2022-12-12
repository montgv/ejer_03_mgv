import java.io.*;
import java.util.Scanner;

public class ejer_03_mgv {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("==========MENU==========");
            System.out.println("1. Comprobar el tamaño del fichero existente.");
            System.out.println("2. Añadir una nueva persona.");
            System.out.println("3. Modificar a una persona ya existente.");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch (opcion) {
                case 1:
                    comprobarFichero();
                case 2:
                    aniadirPersona();
                    break;
                case 3:
                    modificarPersona();
                    break;
                case 0:
                    salir = true;
            }
        } while (!salir);
    }

    private static void comprobarFichero() throws IOException {
        File fichero = new File("persona.dat");

        if (fichero.length() < 108) {
            DataInputStream dip = new DataInputStream(new FileInputStream(fichero));

            String nombre = dip.readUTF();
            int edad = dip.readInt();
            String ciudad = dip.readUTF();

            dip.close();

            File ficheroDef = new File("persona.dat");

            RandomAccessFile raf = new RandomAccessFile(ficheroDef, "rw");

            int indice = 1;
            raf.writeInt(indice);
            StringBuffer buffer = new StringBuffer(nombre);
            buffer.setLength(30);
            raf.writeChars(buffer.toString());
            raf.writeInt(edad);
            StringBuffer buffer2 = new StringBuffer(ciudad);
            buffer2.setLength(20);
            raf.writeChars(buffer2.toString());

            raf.close();

            System.out.println("Fichero comprobado.");
            System.out.println();
        }
    }

    private static int leerIndice() throws IOException {
        File ficheroDef = new File("persona.dat");

        RandomAccessFile raf = new RandomAccessFile(ficheroDef, "rw");

        raf.seek(0);

        return raf.readInt();
    }

    private static void aniadirPersona() throws IOException {
        File ficheroDef = new File("persona.dat");
        RandomAccessFile raf = new RandomAccessFile(ficheroDef, "rw");

        int indice = leerIndice();
        System.out.println("Introduce el nombre completo de la persona: ");
        String nombreCompleto = sc.nextLine();
        System.out.println("Introduce la edad de la persona: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce la ciudad de nacimiento de la persona: ");
        String ciudad = sc.nextLine();
        System.out.println();

        long posicion = (indice) * 108;
        raf.seek(posicion);
        raf.writeInt(indice + 1);
        StringBuffer buffer = new StringBuffer(nombreCompleto);
        buffer.setLength(30);
        raf.writeChars(buffer.toString());
        raf.writeInt(edad);
        StringBuffer buffer2 = new StringBuffer(ciudad);
        buffer2.setLength(20);
        raf.writeChars(buffer2.toString());

        raf.close();
    }

    private static void modificarPersona() throws IOException {
        File ficheroDef = new File("persona.dat");
        RandomAccessFile raf = new RandomAccessFile(ficheroDef, "rw");

        System.out.println("Introduce el indice de la persona a modificar: ");
        int indiceBuscar = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce la edad a modificar: ");
        int edadMod = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce la ciudad a modificar: ");
        String ciudadMod = sc.nextLine();
        System.out.println();

        long posicion = (indiceBuscar - 1) * 108;
        posicion = posicion + 4 + 62;
        raf.seek(posicion);
        raf.writeInt(edadMod);
        StringBuffer buffer = new StringBuffer(ciudadMod);
        buffer.setLength(20);
        raf.writeChars(buffer.toString());

        raf.close();
    }
}