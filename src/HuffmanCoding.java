import java.util.HashMap;
import java.util.Map;

public class HuffmanCoding {
    private HuffmanNode root;
    private Map<Character, String> huffmanCodeMap;

    public HuffmanCoding(String text) {
        Map<Character, Integer> frequencyMap = buildFrequencyMap(text);
        root = buildHuffmanTree(frequencyMap);
        huffmanCodeMap = buildHuffmanCodeMap();
    }

    private Map<Character, Integer> buildFrequencyMap(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    private HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.insert(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            HuffmanNode s1 = pq.delMin();
            HuffmanNode s2 = pq.delMin();
            HuffmanNode s_new = new HuffmanNode(s1.frequency + s2.frequency, s1, s2);
            pq.insert(s_new);
        }
        return pq.delMin();
    }

    private Map<Character, String> buildHuffmanCodeMap() {
        Map<Character, String> huffmanCodeMap = new HashMap<>();
        buildHuffmanCodeMap(root, "", huffmanCodeMap);
        return huffmanCodeMap;
    }

    private void buildHuffmanCodeMap(HuffmanNode node, String code, Map<Character, String> huffmanCodeMap) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            huffmanCodeMap.put(node.character, code);
        }
        buildHuffmanCodeMap(node.left, code + "0", huffmanCodeMap);
        buildHuffmanCodeMap(node.right, code + "1", huffmanCodeMap);
    }

    public String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(huffmanCodeMap.get(c));
        }
        return encodedText.toString();
    }

    public String decode(String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode current = root;
        for (char bit : encodedText.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;
            if (current.left == null && current.right == null) {
                decodedText.append(current.character);
                current = root;
            }
        }
        return decodedText.toString();
    }

    public void printHuffmanCodes() {
        for (Map.Entry<Character, String> entry : huffmanCodeMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        String text = "a b c";
        HuffmanCoding huffmanCoding = new HuffmanCoding(text);
        huffmanCoding.printHuffmanCodes();

        String encodedText = huffmanCoding.encode(text);
        System.out.println("Encoded: " + encodedText);

        String decodedText = huffmanCoding.decode(encodedText);
        System.out.println("Decoded: " + decodedText);
    }
}
