package net.isogen;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Ian Murphy on 8/29/2016.
 */
public class SudokuGenerator extends Generator {
    private final int _maxlevels = 512;
    private final int _nthreshhold = 10;
    private int _dim;
    private int _n;
    private long _hash;
    private int _difficulty;
    private int [][] _field;

    public SudokuGenerator(String username, int level, String outputFile) {
        _username = username;
        _level = level;
        _outputFile = outputFile;
        setGenParams();
        generate();
        scramble();
        removePercentage((float)_difficulty/1.1F);
    }

    private void setGenParams(){
        _n = _level/_nthreshhold + 3;

        _dim = _n*_n;
        _hash = quickHash(_username.getBytes());
        _difficulty = _level%_nthreshhold;
    }

    private long quickHash(byte [] sequence){
        int len = sequence.length;
        long hash = 0;
        for (int i = 0; i<len; i++){
            hash += sequence[i];
            hash = hash << 2;
        }
        return hash;
    }

    public void generate() {
        _field = new int[_n*_n][_n*_n];
        for (int i = 0; i <_n *_n; i++) {
            for (int j = 0; j < _n * _n; j++) {
                _field[i][j] = (i * _n + i / _n + j) % (_n * _n) + 1;
            }
        }
        scramble();
        if (!checkAll()) {
            System.out.println("Not a valid Sudoku board");
            throw new Error();
        }
    }

    private void scramble(){
        Random gen = new Random(_hash);
        for (int row = 0; row< _dim; row++){
            int swap = (int) (gen.nextDouble() * (_dim-row-1));
            swapRows(_field[_dim-row-1], _field[swap]);
        }
        for (int col = 0; col < _dim; col++){
            int swap = (int) (gen.nextDouble() * (_dim-col-1));
            swapCols(_dim-col-1, swap);
        }
    }

    public void print(String format){
        for (int row = 0; row<_dim; row++){
            if(row % _n ==0){
                for (int i = 0; i < _dim; i++) System.out.print("  ");
                for (int i = 0; i < _n; i++) System.out.print(" ");
                System.out.println();
            }
            for (int col = 0;
                 col<_dim;
                 col++)
            {
                if(col % _n == 0) System.out.print(" ");
                System.out.print(String.format( "%1" + format, _field[row][col]) + " ");
            }
            System.out.println();
        }
    }

    private boolean checkAll(){
        HashSet<Integer> rowSet = new HashSet<>(_dim*_dim);
        for (int row = 0; row<_dim; row++)
        {
            for (int col = 0; col<_dim; col++)
                rowSet.add(_field[row][col]);
            if(rowSet.size() < _dim) return false;
            rowSet.clear();
        }
        return true;
    }

    private void swapRows(int[] r1, int[] r2){
        int [] hold = new int[r1.length];
        System.arraycopy(r1, 0, hold, 0, r1.length);
        System.arraycopy(r2, 0, r1, 0, r1.length);
        System.arraycopy(hold, 0, r1, 0, r1.length);
    }

    private void swapCols(int col1, int col2){
        int hold;
        for (int i= 0;i<_dim;i++) {
            hold = _field[i][col1];
            _field[i][col1] = _field[i][col2];
            _field[i][col2] = hold;
        }
    }

    public void removePercentage(float percent){
        Random gen = new Random(_hash + 11);
        int numToRemove = (int)((int)(_dim*_dim)*(percent/100));
        for (int i = 0; i<numToRemove; i++){
            int row = (int)(gen.nextDouble()*_dim);
            int col = (int)(gen.nextDouble()*_dim);
            _field[row][col] = -1;
        }
    }

    public int getDimention(){
        return _dim;
    }

    public String getField(){
        StringBuilder sb = new StringBuilder();
        for(int [] row:_field){
            for (int i : row){
                sb.append(String.valueOf(i));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public void writeOutput() {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(_outputFile));
            output.write(String.valueOf(_n) + "|");
            for (int row = 0; row<_dim; row++){
                for (int col = 0; col<_dim; col++){
                    String value = String.valueOf(_field[row][col]);
                    output.write(value + " ");
                }
                output.write("\n");
            }
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
