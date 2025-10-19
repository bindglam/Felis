#version 330 core

const float AMBIENT = 0.2f;

out vec4 FragColor;

in vec3 color;
in vec2 texCoord;
in vec3 normal;
in vec3 position;

uniform sampler2D tex0;

uniform vec4 lightColor;
uniform vec3 lightPos;
uniform vec3 camPos;

void main() {
    // diffuse lighting
    vec3 norm = normalize(normal);
    vec3 lightDirection = normalize(lightPos - position);
    float diffuse = max(dot(norm, lightDirection), 0.0f);

    // specular lighting
    float specularLight = 0.50f;
    vec3 viewDirection = normalize(camPos - position);
    vec3 reflectionDirection = reflect(-lightDirection, norm);
    float specAmount = pow(max(dot(viewDirection, reflectionDirection), 0.0f), 8);
    float specular = specAmount * specularLight;

    FragColor = texture(tex0, texCoord) * vec4(color, 1.0) * lightColor * (diffuse + AMBIENT + specular);
}