package com.cibertec_LP2.ciberbus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec_LP2.ciberbus.dto.DestinoInfo;

import java.util.List;

@Controller
public class DestinoController {

    @GetMapping("/destino")
    public String destino(Model model) {
        List<DestinoInfo> listaDestinos = List.of(
                new DestinoInfo("Lima", "plazaLima.png",
                        "Capital del Perú, reconocida por su centro histórico, la Costa Verde y su gastronomía de nivel mundial."),
                new DestinoInfo("Ica", "huacachinaIca.png",
                        "Famosa por su oasis de Huacachina, sus viñedos y las dunas del desierto que ofrecen experiencias únicas."),
                new DestinoInfo("Cusco", "plazaCusco.png",
                        "Capital histórica del Perú y puerta de entrada a Machu Picchu, con una mezcla única de cultura inca y colonial."),
                new DestinoInfo("Chachapoyas", "Amazonas.png",
                        "Conocida por su naturaleza exuberante y sitios arqueológicos impresionantes como la fortaleza de Kuélap."),
                new DestinoInfo("Huaraz", "huaraz.png",
                        "Capital del alpinismo peruano, ubicada en la Cordillera Blanca y famosa por sus paisajes nevados y lagunas."),
                new DestinoInfo("Abancay", "abancay.png",
                        "Capital de Apurímac, conocida como la Ciudad de la Eterna Primavera por su clima templado y paisajes andinos."),
                new DestinoInfo("Arequipa", "arequipa.png",
                        "La Ciudad Blanca, destaca por su arquitectura en sillar colonial, el imponente volcán Misti y su gastronomía."),
                new DestinoInfo("Ayacucho", "ayacucho.png",
                        "Conocida como la Ciudad de las Iglesias, destacando por su arte popular, su historia y celebraciones religiosas."),
                new DestinoInfo("Cajamarca", "cajamarca.jpg",
                        "Célebre por sus carnavales, sus baños termales y por ser escenario histórico del encuentro inca y español."),
                new DestinoInfo("Huancavelica", "huancavelica.jpg",
                        "Conocida por sus majestuosos paisajes andinos, tradición minera y relajantes aguas termales."),
                new DestinoInfo("Huánuco", "huanuco.jpg",
                        "La Ciudad del Mejor Clima del Mundo, rodeada de valles y paisajes entre la sierra y la selva alta."),
                new DestinoInfo("Huancayo", "huancayo.jpg",
                        "Capital del Valle del Mantaro, famosa por su feria dominical, artesanía y vibrante riqueza cultural."),
                new DestinoInfo("Trujillo", "trujillo.jpg",
                        "La Capital de la Primavera, famosa por sus festivales de marinera, Chan Chan y las huacas del Sol y de la Luna."),
                new DestinoInfo("Chiclayo", "chiclayo.jpg",
                        "La Capital de la Amistad, famosa por su calidez, su gastronomía norteña y sitios arqueológicos de Sipán."),
                new DestinoInfo("Cerro de Pasco", "pasco.jpg",
                        "Ciudad de gran altitud en la sierra central, reconocida por su historia minera y paisajes montañosos."),
                new DestinoInfo("Piura", "piura.png",
                        "Famosa por sus cálidas playas como Máncora, su tradición artesanal y su deliciosa gastronomía norteña."),
                new DestinoInfo("Puno", "puno.jpg",
                        "Capital del folclore peruano a orillas del legendario Lago Titicaca y sede de la Fiesta de la Candelaria."),
                new DestinoInfo("Moyobamba", "moyobamba.png",
                        "La Ciudad de las Orquídeas, en la Amazonía peruana, famosa por sus miradores naturales y aguas termales."),
                new DestinoInfo("Tumbes", "tumbes.jpg",
                        "Destino tropical de la costa norte, reconocido por sus hermosos manglares y paradisíacas playas cálidas."),
                new DestinoInfo("Pucallpa", "pucallpa.jpg",
                        "La Ciudad del Color y del Calor, en la Amazonía peruana, reconocida por su cultura shipibo y paisajes selváticos."));

        model.addAttribute("destinos", listaDestinos);
        return "ventas/destino";
    }

}
