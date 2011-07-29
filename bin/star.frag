uniform sampler2D tex, tex1;

uniform float noise_offset;

uniform int layers;

uniform float rots;

void main()
{
	vec2 noise_lookup = gl_TexCoord[ 0 ].st;
	
	vec2 offsets = vec2( 0, 0 );
	
	offsets += texture2D( tex1, noise_lookup + vec2( noise_offset, noise_offset ) ).xy;
	
	offsets += texture2D( tex1, noise_lookup + vec2( noise_offset, -noise_offset ) ).xy;
	
	offsets += texture2D( tex1, noise_lookup + vec2( -noise_offset, noise_offset ) ).xy;
	
	offsets += texture2D( tex1, noise_lookup + vec2( -noise_offset, -noise_offset ) ).xy;
	
	offsets /= 200;
	
	vec4 color = vec4( .25, .25, .1, 0 );
	
	for( int l=0; l<layers; l++ )
	{
		vec2 rot = vec2( rots * ( l - 1 ), 0);
		
		//float s = gl_TexCoord[ 0 ].s + offsets.x + rots[ 0 ];
		
		//float t = gl_TexCoord[ 0 ].t + offsets.t + rots[ 1 ];
		
		//~ float s = gl_TexCoord[ 0 ].s;
		//~ 
		//~ float t = gl_TexCoord[ 0 ].t;
		
		vec2 tex_coords = gl_TexCoord[ 0 ].st;
		
		tex_coords.x = 1 - tex_coords.x;
		
		if( ( l % 2 ) == 1 )
		{
			tex_coords.y = 1 - tex_coords.y;
		}
		
		color += texture2D( tex, tex_coords + offsets + rot ) * 0.5;
		
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

