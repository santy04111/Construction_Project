@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @PostMapping("/pagos")
    public ResponseEntity<String> manejarNotificacionPago(@RequestBody String datosNotificacion) {
        return ResponseEntity.ok("Notificación recibida correctamente");
    }
}