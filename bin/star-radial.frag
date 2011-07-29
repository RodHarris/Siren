uniform sampler2D tex, tex1;

uniform float noise_offset;

uniform int layers;

uniform float rots;

void main()
{
	vec2 noise_lookup = gl_TexCoord[ 0 ].st;
	
	noise_lookup.x -= 0.5;
	
	noise_lookup.y -= 0.5;
	
	float theta = atan( noise_lookup.y / noise_lookup.x );
	
	float r = sqrt( noise_lookup.x * noise_lookup.x + noise_lookup.y * noise_lookup.y );
	
	r -= noise_offset;
	
	noise_lookup.x = r * cos( theta );
	
	noise_lookup.y = r * sin( theta );
	
	//noise_lookup.y += noise_offset;
	
	//noise_lookup.x += noise_offset;
	
	vec2 offsets = texture2D( tex1, noise_lookup ).xy;
	
	offsets /= 25;
	
	vec4 color = vec4( .25, .25, .1, 0 );
	
	for( int l=0; l<layers; l++ )
	{
		vec2 rot = vec2( rots * ( l - 1 ), 0);
		
		//float s = gl_TexCoord[ 0 ].s + offsets.x + rots[ 0 ];
		
		//float t = gl_TexCoord[ 0 ].t + offsets.t + rots[ 1 ];
		
		//~ float s = gl_TexCoord[ 0 ].s;
		//~ 
		//~ float t = gl_TexCoord[ 0 ].t;
		
		
		color += texture2D( tex, gl_TexCoord[ 0 ].st + offsets + rot ) * 0.5;
		
		//color += texture2D( tex, gl_TexCoord[ 0 ].st + rot );
	}
	
	//gl_FragColor = texture2D( tex1, gl_TexCoord[ 0 ].st );
	
	float refr = 0.45;
	float refg = 0.6;
	float refb = 0.85;
	float refa = 0.5;
	
	vec4 contrast = vec4( refr, refg, refb, refa ) - color;
	
	color = vec4( .5, .5, .5, .5 ) - contrast * 2;
	
	gl_FragColor = color;
}
