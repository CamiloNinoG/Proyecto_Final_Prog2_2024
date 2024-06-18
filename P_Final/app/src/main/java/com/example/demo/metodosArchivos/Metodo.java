package com.example.demo.metodosArchivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Libro;
import com.example.demo.domain.Prestamo;
import com.example.demo.domain.Usuario;

@Service
public class Metodo {

    // cargar libros desde archivo de texto
    public void cargarLibros(ArrayList<Libro> libros, String filePath) {
        try (BufferedReader lector = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println("Línea leída: " + linea);
                String[] componentes = linea.split(";");

                if (componentes.length == 10) {
                    long id_libro = Long.parseLong(componentes[0]);
                    String titulo = componentes[1];
                    String autor = componentes[2];
                    String genero = componentes[3];
                    int año = Integer.parseInt(componentes[4]);
                    int mes = Integer.parseInt(componentes[5]);
                    int dia = Integer.parseInt(componentes[6]);
                    String isbn = componentes[7];
                    int stock = Integer.parseInt(componentes[8]);
                    String descripcion = componentes[9];

                    LocalDate fechaPublicacion = LocalDate.of(año, mes, dia);
                    Libro libro = new Libro(id_libro, titulo, autor, genero, fechaPublicacion, isbn, stock,
                            descripcion);
                    libros.add(libro);
                } else {
                    System.err.println("La línea no tiene el formato esperado: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // escribir libro al archivo
    public void agregarLibro(String filePath, Libro libro) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(filePath, true))) {
            String linea = String.format("%d;%s;%s;%s;%d;%d;%d;%s;%d;%s",
                    libro.getId(),
                    libro.getTitle(),
                    libro.getAutor(),
                    libro.getGender(),
                    libro.getDate().getYear(),
                    libro.getDate().getMonthValue(),
                    libro.getDate().getDayOfMonth(),
                    libro.getIsbn(),
                    libro.getStock(),
                    libro.getDescription());

            escritor.write(linea);
            escritor.newLine();

            System.out.println("Libro agregado exitosamente al archivo.");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // eliminar un libro
    public void eliminarLibro(String filePath, Long id) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        lines.removeIf(line -> line.startsWith(id + ";"));

        Files.write(path, lines);
    }

    // actualizar libro
    public void actualizarLibro(String filePath, Long id, Libro libroActualizado) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] components = line.split(";");

                if (components.length > 0 && Long.parseLong(components[0].trim()) == id) {
                    String nuevaLinea = String.format("%d;%s;%s;%s;%d;%d;%d;%s;%d;%s",
                            libroActualizado.getId(),
                            libroActualizado.getTitle(),
                            libroActualizado.getAutor(),
                            libroActualizado.getGender(),
                            libroActualizado.getDate().getYear(),
                            libroActualizado.getDate().getMonthValue(),
                            libroActualizado.getDate().getDayOfMonth(),
                            libroActualizado.getIsbn(),
                            libroActualizado.getStock(),
                            libroActualizado.getDescription());

                    lines.set(i, nuevaLinea);
                    break;
                }
            }

            Files.write(path, lines);
            System.out.println("Línea actualizada en el archivo de texto.");

        } catch (IOException e) {
            System.err.println("Error al actualizar la línea en el archivo: " + e.getMessage());
        }
    }

    // cargar usuarios desde archivo
    public void cargarUsuarios(ArrayList<Usuario> usuarios, String filePath) {
        try (BufferedReader lector = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println("Línea leída: " + linea);
                String[] componente = linea.split(";");
                if (componente.length == 6) {
                    long id_usuario = Long.parseLong(componente[0]);
                    String name = componente[1];
                    String last_name = componente[2];
                    String email = componente[3];
                    String password = componente[4];
                    String rol = componente[5];
                    Usuario usuario = new Usuario(id_usuario, name, last_name, email, password, rol);
                    usuarios.add(usuario);
                } else {
                    System.err.println("Error: La línea no tiene el formato esperado: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // escribir usuarios en el archivo
    public void agergarUsuario(String filePath, Usuario usuario) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(filePath, true))) {
            String linea = String.format("%d;%s;%s;%s;%s;%s",
                    usuario.getId(),
                    usuario.getName(),
                    usuario.getLast_name(),
                    usuario.getEmail(),
                    usuario.getPassword(),
                    usuario.getRol());
            escritor.write(linea);
            escritor.newLine();
            System.out.println("Usuarios escritos correctamente en el archivo.");
        }

        catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // eliminar usuario
    public void eliminarUsuario(String filePath, Long id) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        lines.removeIf(line -> line.startsWith(id + ";"));

        Files.write(path, lines);
    }

    // actualizar usuario
    public void actualizarUsuario(String filePath, Long id, Usuario _usuario) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] components = line.split(";");

                if (components.length > 0 && Long.parseLong(components[0].trim()) == id) {
                    String nuevaLinea = String.format("%d;%s;%s;%s;%s;%s",
                            _usuario.getId(),
                            _usuario.getName());
                    _usuario.getLast_name();
                    _usuario.getEmail();
                    _usuario.getPassword();
                    _usuario.getRol();

                    lines.set(i, nuevaLinea);
                    break;
                }
            }

            Files.write(path, lines);
            System.out.println("Línea actualizada en el archivo de texto.");

        } catch (IOException e) {
            System.err.println("Error al actualizar la línea en el archivo: " + e.getMessage());
        }
    }

    // caragr prestamos desde txt
    public void cargarPrestamos(ArrayList<Prestamo> prestamos, String filePath, ArrayList<Libro> libros,
            ArrayList<Usuario> usuarios) {
        try (BufferedReader lector = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println("Línea leída: " + linea);
                String[] componente = linea.split(";");
                if (componente.length == 6) {
                    long id_prestamo = Long.parseLong(componente[0]);
                    String isbn = componente[1];
                    long id_user = Long.parseLong(componente[2]);
                    int año = Integer.parseInt(componente[3]);
                    int mes = Integer.parseInt(componente[4]);
                    int dia = Integer.parseInt(componente[5]);

                    boolean bandera = true;

                    for (Libro a : libros) {
                        if (a.getIsbn().equals(isbn)) {
                            a.setStock(a.getStock() - 1);
                            System.out.println("libro encontrado");
                            bandera = true;
                            break;
                        }
                    }

                    for (Usuario a : usuarios) {
                        if (a.getId() == id_user) {
                            System.out.println("usuario encontrado");
                            bandera = true;
                            break;
                        }
                    }

                    if (bandera) {
                        LocalDate fechaPrestamo = LocalDate.of(año, mes, dia);
                        Prestamo prestamo_nuevo = new Prestamo(id_prestamo, isbn, id_user, fechaPrestamo);
                        prestamos.add(prestamo_nuevo);
                    }

                } else {
                    System.err.println("Error: La línea no tiene el formato esperado: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // escribir prestamo en el txt
    public void agergarPrestamo(String filePath, Prestamo prestamo) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(filePath, true))) {
            String linea = String.format("%d;%s;%d;%d;%d;%d",
                    prestamo.getId(),
                    prestamo.getIsbn(),
                    prestamo.getId_user(),
                    prestamo.getDate().getYear(),
                    prestamo.getDate().getMonthValue(),
                    prestamo.getDate().getDayOfMonth());

            escritor.write(linea);
            escritor.newLine();
            System.out.println("Préstamos escritos correctamente en el archivo.");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    //eliminar prestamo
    public void eliminarPrestamo(String filePath, Long id) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        lines.removeIf(line -> line.startsWith(id + ";"));

        Files.write(path, lines);
    }

    // actualizar prestamo
    public void actualizarPrestamo(String filePath, Long id, Prestamo _prestamo) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] components = line.split(";");

                if (components.length > 0 && Long.parseLong(components[0].trim()) == id) {
                    String nuevaLinea = String.format("%d;%s;%s;%s;%s;%s",
                    _prestamo.getId(),
                    _prestamo.getIsbn(),
                    _prestamo.getId_user(),
                    _prestamo.getDate().getYear(),
                    _prestamo.getDate().getMonthValue(),
                    _prestamo.getDate().getDayOfMonth());

                    lines.set(i, nuevaLinea);
                    break;
                }
            }

            Files.write(path, lines);
            System.out.println("Línea actualizada en el archivo de texto.");

        } catch (IOException e) {
            System.err.println("Error al actualizar la línea en el archivo: " + e.getMessage());
        }
    }

} // Metodo class
