package builders;

import org.json.JSONObject;

public class ProductPayloadBuilder
{
    private final JSONObject payload;

    public ProductPayloadBuilder() {
        payload = new JSONObject();
    }

    public ProductPayloadBuilder withTitle(String title) {
        payload.put("title", title);
        return this;
    }

    public ProductPayloadBuilder withPrice(double price) {
        payload.put("price", price);
        return this;
    }

    public ProductPayloadBuilder withDescription(String description) {
        payload.put("description", description);
        return this;
    }

    public ProductPayloadBuilder withCategory(String category) {
        payload.put("category", category);
        return this;
    }

    public String build() {
        return payload.toString();
    }
}
