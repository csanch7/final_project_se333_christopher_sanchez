from fastmcp import FastMCP
import xml.etree.ElementTree as ET

mcp = FastMCP("SE333 MCP Server 🚀")

@mcp.tool
def add(a: int, b: int) -> int:
    """Add two numbers"""
    return a + b


@mcp.tool
def coverage_heatmap(jacoco_path: str = "target/site/jacoco/jacoco.xml"):
    """Parse JaCoCo XML and return low-coverage classes."""
    
    tree = ET.parse(jacoco_path)
    root = tree.getroot()

    results = []

    for package in root.findall("package"):
        for cls in package.findall("class"):
            name = cls.get("name")

            missed = 0
            covered = 0

            for counter in cls.findall("counter"):
                if counter.get("type") == "LINE":
                    missed = int(counter.get("missed"))
                    covered = int(counter.get("covered"))

            total = missed + covered
            coverage = 0 if total == 0 else round((covered / total) * 100, 2)

            results.append({
                "class": name,
                "line_coverage": coverage
            })

    results.sort(key=lambda x: x["line_coverage"])

    return results


if __name__ == "__main__":
    mcp.run(transport="sse")