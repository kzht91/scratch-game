package org.example.services;


import org.apache.commons.lang3.tuple.Pair;
import org.example.models.WinCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Matcher {

    private final Map<Integer, Pair<String, WinCombination>> sameSymbols = new HashMap<>();
    private final List<Pair<String, WinCombination>> linearSymbols = new ArrayList<>();

    public Matcher(Map<String, WinCombination> winCombinations) {
        for (Map.Entry<String, WinCombination> entry : winCombinations.entrySet()) {
            WinCombination win = entry.getValue();
            switch (win.getWhen()) {
                case "same_symbols" -> sameSymbols.put(win.getCount(), Pair.of(entry.getKey(), win));
                case "linear_symbols" -> linearSymbols.add(Pair.of(entry.getKey(), win));
            }
        }
    }

    public List<SymbolsMatched> matchSameSymbols(String[][] board) {
        Map<String, Integer> symbols = new HashMap<>();

        for (String[] row : board) {
            for (String symbol : row) {
                symbols.merge(symbol, 1, Integer::sum);
            }
        }

        List<SymbolsMatched> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            Integer count = entry.getValue();
            Pair<String, WinCombination> descriptor = sameSymbols.get(count);

            if (descriptor != null) {
                SymbolsMatched symbolsMatched = new SymbolsMatched(
                        entry.getKey(),
                        descriptor.getLeft(),
                        descriptor.getRight().getGroup(),
                        descriptor.getRight().getRewardMultiplier()
                );
                result.add(symbolsMatched);
            }
        }
        return result;
    }

    public List<SymbolsMatched> matchLinearSymbols(String[][] board) {
        List<SymbolsMatched> results = new ArrayList<>();

        for (Pair<String, WinCombination> cd : linearSymbols) {
            for (List<String> coveredArea : cd.getRight().getCoveredAreas()) {
                List<Pair<Integer, Integer>> coordinates = coveredArea.stream()
                        .map(s -> s.split(":"))
                        .map(s -> Pair.of(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                        .collect(Collectors.toList());

                if (isCovered(board, coordinates)) {
                    String symbol = board[coordinates.get(0).getLeft()][coordinates.get(0).getRight()];
                    SymbolsMatched symbolsMatched = new SymbolsMatched(
                            symbol,
                            cd.getLeft(),
                            cd.getRight().getGroup(),
                            cd.getRight().getRewardMultiplier()
                    );
                    results.add(symbolsMatched);
                }
            }
        }

        return results;
    }

    private boolean isCovered(String[][] board, List<Pair<Integer, Integer>> coveredArea) {
        String symbol = board[coveredArea.get(0).getLeft()][coveredArea.get(0).getRight()];
        for (int i = 1; i < coveredArea.size(); i++) {
            Pair<Integer, Integer> next = coveredArea.get(i);
            if (!symbol.equals(board[next.getLeft()][next.getRight()])) {
                return false;
            }
        }
        return true;
    }
}