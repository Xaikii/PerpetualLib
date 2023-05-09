package net.perpetualeve.perpetuallib.misc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class RunConfigFixer
{
	public static void main(String...args)
	{
		String folder = args[0];
		System.out.println("Folder: "+folder);
		String file = args[1];
		System.out.println("File: "+file);
		try
		{
			Path folderPath = Paths.get(folder);
			for(Path path : wrap(Files.newDirectoryStream(folderPath).iterator()))
			{
				if(path.getFileName().toString().endsWith(".launch"))
				{
					insertConfig(path, file, "org.eclipse.jdt.launching.VM_ARGUMENTS", "key");
				}
			}
			folderPath = folderPath.resolve(".idea").resolve("runConfigurations");
			if(Files.exists(folderPath))
			{
				for(Path path : wrap(Files.newDirectoryStream(folderPath).iterator()))
				{
					if(path.getFileName().toString().endsWith(".xml"))
					{
						insertConfig(path, file, "VM_PARAMETERS", "name");
					}
				}	
			}
			Path path = Paths.get(folder).resolve(".vscode").resolve("launch.json");
			if (Files.exists(path)) {
				insertJsonConfig(path, file);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Iterable<Path> wrap(Iterator<Path> iter)
	{
		return () -> iter;
	}
	
	private static void insertConfig(Path path, String filePath, String arg, String key) throws IOException, SAXException, ParserConfigurationException, TransformerConfigurationException, TransformerException
	{
		boolean[] result = new boolean[1];
		Document doc = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(Files.newInputStream(path));
		findNode(doc.getChildNodes(), arg, key, T -> {
			Node valueNode = T.getAttributes().getNamedItem("value");
			List<String> parameters = new ObjectArrayList<>(valueNode.getNodeValue().split(" "));
			int index = parameters.indexOf("-p");
			if(index == -1) return;
			String args = parameters.get(index+1);
			if(args.contains(filePath)) return;
			parameters.set(index+1, args + File.pathSeparator + filePath);
			StringJoiner joiner = new StringJoiner(" ");
			parameters.forEach(joiner::add);
			valueNode.setNodeValue(joiner.toString());
			result[0] = true;
			System.out.println("Fixed: "+path.toString());
		});
		if(result[0])
		{
			try(OutputStream stream = Files.newOutputStream(path)) {
				TransformerFactory.newDefaultInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(stream));				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void findNode(NodeList list, String finder, String key, Consumer<Node> result)
	{
		for(int i = 0,m=list.getLength();i<m;i++)
		{
			Node node = list.item(i);
			NamedNodeMap map = node.getAttributes();
			if(map != null)
			{
				Node subNode = map.getNamedItem(key);
				if(subNode != null && finder.equals(subNode.getNodeValue()))
				{
					result.accept(node);
				}
			}
			findNode(node.getChildNodes(), finder, key, result);
		}
	}

	private static void insertJsonConfig(Path path, String filePath) throws IOException {
		boolean result = false;
		JsonObject object = null;
		try (JsonReader reader = new JsonReader(new FileReader(path.toFile()))) {
			object = JsonParser.parseReader(reader).getAsJsonObject();
		}
		JsonArray configurations = object.getAsJsonArray("configurations");
		for (JsonElement e : configurations) {
			JsonObject config = e.getAsJsonObject();
			JsonPrimitive vmArgs = config.getAsJsonPrimitive("vmArgs");

			List<String> parameters = new ObjectArrayList<>(vmArgs.getAsString().split(" "));
			int index = parameters.indexOf("-p");
			if(index == -1) continue;
			String args = parameters.get(index+1);
			if(args.contains(filePath)) continue;
			parameters.set(index+1, args + File.pathSeparator + filePath);
			StringJoiner joiner = new StringJoiner(" ");
			parameters.forEach(joiner::add);
			config.addProperty("vmArgs", joiner.toString());
			result = true;
			System.out.println("Fixed: "+path.toString()+" "+config.getAsJsonPrimitive("name").getAsString());
		}

		if (result) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			try (JsonWriter writer = gson.newJsonWriter(new FileWriter(path.toFile()))) {
				gson.toJson(object, writer);
			}
		}
	}
}