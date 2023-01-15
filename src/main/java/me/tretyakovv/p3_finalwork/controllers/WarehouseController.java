package me.tretyakovv.p3_finalwork.controllers;

import io.swagger.v3.oas.annotations.Operation;
import me.tretyakovv.p3_finalwork.model.Color;
import me.tretyakovv.p3_finalwork.model.Size;
import me.tretyakovv.p3_finalwork.model.Socks;
import me.tretyakovv.p3_finalwork.services.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
public class WarehouseController {

    private WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    @Operation(summary = "Поступления носков")
    public ResponseEntity<Boolean> postSocks(@RequestBody Socks socks) {
        return ResponseEntity.ok(warehouseService.postSocks(socks));
    }

    @PutMapping
    @Operation(summary = "Отгрузка носков")
    public ResponseEntity<Boolean> putSocks(@RequestBody Socks socks) {
        if (warehouseService.releaseSocks(socks)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    @Operation(summary = "Утлизация носков")
    public ResponseEntity<Boolean> utilizationSocks(@RequestBody Socks socks) {
        if (warehouseService.releaseSocks(socks)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    @Operation(summary = "Получить список всех носков на складе")
    public ResponseEntity<?> getAllSocksIn() {
        return ResponseEntity.ok(warehouseService.getListSocks());
    }
    @GetMapping("/findSocksByParameters")
    @Operation(summary = "Получить текущий остаток носков, по параметрам")
    public ResponseEntity<Integer> getcurrenBalanceByParameters(@RequestParam Color color,
                                                                @RequestParam Size size,
                                                                @RequestParam int cottonMin,
                                                                @RequestParam int cottonMax) {
        return ResponseEntity.ok(warehouseService.currenBalanceByParameters(color, size, cottonMin, cottonMax));
    }
}
