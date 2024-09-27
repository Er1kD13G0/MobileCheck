<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\StockMovement;

class StockMovementController extends Controller
{
    public function index()
    {
        $stockMovements = StockMovement::all();
        return response()->json($stockMovements);
    }

    public function store(Request $request)
    {
        $stockMovement = new StockMovement();
        $stockMovement->product_id = $request->input('product_id');
        $stockMovement->quantity = $request->input('quantity');
        $stockMovement->type = $request->input('type');
        $stockMovement->save();
        return response()->json($stockMovement);
    }

    public function show($id)
    {
        $stockMovement = StockMovement::find($id);
        return response()->json($stockMovement);
    }

    public function update(Request $request, $id)
    {
        $stockMovement = StockMovement::find($id);
        $stockMovement->product_id = $request->input('product_id');
        $stockMovement->quantity = $request->input('quantity');
        $stockMovement->type = $request->input('type');
        $stockMovement->save();
        return response()->json($stockMovement);
    }

    public function destroy($id)
    {
        $stockMovement = StockMovement::find($id);
        $stockMovement->delete();
        return response()->json(['message' => 'Stock movement deleted successfully']);
    }
}

