package katlyn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Katlyn {

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean ejecutar = true;
        LinkedList<Estudiante> estudiantes = new LinkedList<>();

        while (ejecutar) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Verificar si una frase es un palíndromo.");
            System.out.println("2. Cargar lista de un fichero, almacenarlo en hashset y verificar nombres repetidos.");
            System.out.println("3. Ver lista ordenada");
            System.out.println("4. Agregar estudiante al final o al inicio desde la consola.");
            System.out.println("5. Buscar estudiante por carnet.");
            System.out.println("6. Editar estudiante.");
            System.out.println("7. Eliminar estudiante.");
            System.out.println("8. Mostrar lista de estudiantes.");
            System.out.println("9. Salir.");

            int opcion = Integer.parseInt(in.nextLine());

            switch (opcion) {
                case 1:
                    verificarPalindromo();
                    break;
                case 2:
                    estudiantes = leerEstudiantes();
                    verificarNombresRepetidos(estudiantes);
                    break;
                case 3:
                    estudiantes = leerEstudiantes();
                    estudiantes = ordenarLista(estudiantes);
                    imprimirLista(estudiantes);
                    break;
                case 4:
                    estudiantes = leerEstudiantes();
                    agregarEstudianteConsola(estudiantes);
                    break;
                case 5:
                    estudiantes = leerEstudiantes();
                    buscarEstudiante(estudiantes);
                    break;
                case 6:
                    estudiantes = leerEstudiantes();
                    estudiantes = editarEstudiante(estudiantes);
                    break;
                case 7:
                    estudiantes = leerEstudiantes();
                    estudiantes = eliminarEstudiante(estudiantes);
                    break;
                case 8:
                    imprimirLista(estudiantes);
                    break;
                case 9:
                    ejecutar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

            System.out.println("¿Desea ejecutar otra opción? (s/n)");
            String respuesta = in.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                ejecutar = false;
            }
        }
    }

    public static LinkedList<Estudiante> leerEstudiantes() {
        LinkedList<Estudiante> list = new LinkedList<>();
        HashSet<String> nombresCompletos = new HashSet<>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("estudiantes.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int numero = Integer.parseInt(datos[0].trim());
                String carnet = datos[1].trim();
                String nombre = datos[2].trim();
                double nota = Double.parseDouble(datos[3].trim());
                Estudiante estudiante = new Estudiante(numero, carnet, nombre, nota);
                list.add(estudiante);
                if (!nombresCompletos.add(nombre)) {
                    System.out.println("Nombre completo repetido: " + nombre);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return list;
    }

    private static void verificarPalindromo() {
        System.out.println("Ingrese una frase para verificar si es un palíndromo:");
        String frase = in.nextLine();
        if (isPalindrome(frase)) {
            System.out.println("La frase '" + frase + "' es un palíndromo.");
        } else {
            System.out.println("La frase '" + frase + "' no es un palíndromo.");
        }
    }

    private static void verificarNombresRepetidos(LinkedList<Estudiante> estudiantes) {
        HashSet<String> nombresCompletos = new HashSet<>();
        for (Estudiante estudiante : estudiantes) {
            if (!nombresCompletos.add(estudiante.getNombre())) {
                System.out.println("Nombre completo repetido: " + estudiante.getNombre());
            }
        }
    }

    private static void agregarEstudianteConsola(LinkedList<Estudiante> estudiantes) {
        System.out.println("Agregar estudiante al final o al inicio (final/inicio)?");
        String posicion = in.nextLine().trim().toLowerCase();

        if (posicion.equals("final")) {
            System.out.println("Ingrese los datos del estudiante (número, carnet, nombre, nota) separados por comas:");
            String input = in.nextLine().trim();
            String[] datos = input.split(",");
            if (datos.length == 4) {
                int numero = Integer.parseInt(datos[0].trim());
                String carnet = datos[1].trim();
                String nombre = datos[2].trim();
                double nota = Double.parseDouble(datos[3].trim());
                Estudiante nuevo = new Estudiante(numero, carnet, nombre, nota);
                estudiantes.addLast(nuevo);
                escribirEstArchivo(estudiantes);
                System.out.println("Estudiante agregado al final: " + nuevo.getNombre());
            } else {
                System.out.println("Formato incorrecto. El estudiante no fue agregado.");
            }
        } else if (posicion.equals("inicio")) {
            System.out.println("Ingrese los datos del estudiante (número, carnet, nombre, nota) separados por comas:");
            String input = in.nextLine().trim();
            String[] datos = input.split(",");
            if (datos.length == 4) {
                int numero = Integer.parseInt(datos[0].trim());
                String carnet = datos[1].trim();
                String nombre = datos[2].trim();
                double nota = Double.parseDouble(datos[3].trim());
                Estudiante nuevo = new Estudiante(numero, carnet, nombre, nota);
                estudiantes.addFirst(nuevo);
                escribirEstArchivo(estudiantes);
                System.out.println("Estudiante agregado al inicio: " + nuevo.getNombre());
            } else {
                System.out.println("Formato incorrecto. El estudiante no fue agregado.");
            }
        } else {
            System.out.println("Posición incorrecta. No se agregó ningún estudiante.");
        }

    }

    private static void imprimirLista(List<Estudiante> list) {
        for (Estudiante estudiante : list) {
            System.out.println(estudiante.getNumero() + "-" + estudiante.getCarnet() + "-" + estudiante.getNombre() + "-" + estudiante.getNota());
        }
    }

    private static LinkedList<Estudiante> ordenarLista(LinkedList<Estudiante> estudiantes) {
        estudiantes.sort(Comparator.comparing(Estudiante::getNombre));
        return estudiantes;
    }

    private static void buscarEstudiante(LinkedList<Estudiante> estudiantes) {
        System.out.println("");
        System.out.println("Ingrese el carnet a buscar");
        String s = in.nextLine();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCarnet().equals(s)) {
                System.out.println("-----------Encontrado-----------");
                System.out.println("Número: " + estudiante.getNumero());
                System.out.println("Carne: " + estudiante.getCarnet());
                System.out.println("Nombre: " + estudiante.getNombre());
                System.out.println("Nota: " + estudiante.getNota());
            }
        }
    }

    private static LinkedList<Estudiante> editarEstudiante(LinkedList<Estudiante> estudiantes) {
        System.out.println("");
        System.out.println("Ingrese el carnet a editar");
        String s = in.nextLine();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCarnet().equals(s)) {
                try {
                    System.out.println("¿Qué campo desea editar? (numero/nombre/nota)");
                    String campo = in.nextLine().toLowerCase();
                    switch (campo) {
                        case "numero":
                            System.out.println("Ingrese el nuevo número:");
                            int numero = Integer.parseInt(in.nextLine());
                            estudiante.setNumero(numero);
                            escribirEstArchivo(estudiantes);
                            break;
                        case "nombre":
                            System.out.println("Ingrese el nuevo nombre:");
                            String nombre = in.nextLine();
                            estudiante.setNombre(nombre);
                            escribirEstArchivo(estudiantes);
                            break;
                        case "nota":
                            System.out.println("Ingrese la nueva nota:");
                            double nota = Double.parseDouble(in.nextLine());
                            estudiante.setNota(nota);
                            escribirEstArchivo(estudiantes);
                            break;
                        default:
                            System.out.println("Campo incorrecto. No se realizó ninguna modificación.");
                            break;
                    }
                    return estudiantes;
                } catch (NumberFormatException e) {
                    System.out.println("Error: El formato del número o nota no es válido. El estudiante no fue editado.");
                    return estudiantes;
                }
            }
        }
        System.out.println("No se encontró ningún estudiante con ese carnet.");
        return estudiantes;
    }

    private static LinkedList<Estudiante> eliminarEstudiante(LinkedList<Estudiante> estudiantes) {
        System.out.println("");
        System.out.println("Ingrese el carnet a eliminar");
        String s = in.nextLine();
        estudiantes.removeIf(estudiante -> estudiante.getCarnet().equals(s));
        System.out.println("----------------Eliminado----------------");
        escribirEstArchivo(estudiantes);
        return estudiantes;
    }

    public static boolean isPalindrome(String phrase) {
        phrase = phrase.replaceAll("\\s+", "").toLowerCase();
        Stack<Character> stack = new Stack<>();
        int length = phrase.length();
        int halfLength = length / 2;
        for (int i = 0; i < halfLength; i++) {
            stack.push(phrase.charAt(i));
        }
        if (length % 2 != 0) {
            halfLength++;
        }
        for (int i = halfLength; i < length; i++) {
            if (phrase.charAt(i) != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    private static void escribirEstArchivo(LinkedList<Estudiante> estudiantes) {
        File archivo = new File("estudiantes.txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            for (Estudiante estudiante : estudiantes) {
                String linea = estudiante.getNumero() + "," + estudiante.getCarnet() + "," + estudiante.getNombre() + "," + estudiante.getNota();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
